package com.sdklite.backend.service;

import com.sdklite.backend.dto.ProjectContextDTO;
import com.sdklite.backend.mapper.MetadataMapper;
import com.sdklite.backend.model.*;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetadataServiceImpl implements MetadataService {

    private final FileService fileService;
    private final MetadataParserService parserService;
    private final MetadataGraphService graphService;
    private final MocHierarchyService mocHierarchyService;
        private final MetadataMapper metadataMapper;
        private final LockService lockService;
        private final SessionService sessionService;
    private final IarPackagerService packagerService;
    private final VersioningService versioningService;
    
    @org.springframework.beans.factory.annotation.Value("${app.adaptor.base-path}")
    private String adaptorBasePath;

    public MetadataServiceImpl(FileService fileService, 
                               MetadataParserService parserService, 
                               MetadataGraphService graphService,
                               MocHierarchyService mocHierarchyService,
                               MetadataMapper metadataMapper,
                               LockService lockService,
                               SessionService sessionService,
                               IarPackagerService packagerService,
                               VersioningService versioningService) {
        this.fileService = fileService;
        this.parserService = parserService;
        this.graphService = graphService;
        this.mocHierarchyService = mocHierarchyService;
        this.metadataMapper = metadataMapper;
        this.lockService = lockService;
        this.sessionService = sessionService;
        this.packagerService = packagerService;
        this.versioningService = versioningService;
    }

    @Override
    public void publishAdaptor(String username, String timestamp, String adaptorName, String vendor, String technology, String newVersion) throws IOException {
        String lockId = String.join("/", vendor, technology, adaptorName, "latest");
        
        // 1. Verify lock
        lockService.checkLock(lockId).ifPresent(lock -> {
            if (!lock.getUsername().equals(username)) {
                throw new SecurityException("User does not hold the lock for this adaptor");
            }
        });

        Path workArea = fileService.resolveUserTempPath(username, timestamp);
        Path configDir = workArea.resolve("config");
        
        // 2. Package to temporary IAR
        Path tempIar = workArea.resolve(adaptorName + ".iar");
        packagerService.packageConfig(configDir, tempIar);

        // 3. Create target directory
        Path targetDir = java.nio.file.Paths.get(adaptorBasePath, vendor, technology, adaptorName, newVersion);
        if (targetDir.toFile().exists()) {
            throw new IllegalStateException("Version " + newVersion + " already exists");
        }
        java.nio.file.Files.createDirectories(targetDir);

        // 4. Move IAR to final location
        java.nio.file.Files.move(tempIar, targetDir.resolve(adaptorName + ".iar"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // 5. Cleanup session
        sessionService.closeSession(username, timestamp, lockId);
    }

    @Override
    public String suggestNextVersion(String vendor, String technology, String adaptorName) {
        return versioningService.suggestNextVersion(vendor, technology, adaptorName);
    }

    @Override
    public List<ImportDataSource> getDataSourceHierarchy(String username, String timestamp, String adaptorName, int depth) {
        Path metadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        
        try {
            if (!metadataPath.toFile().exists()) return Collections.emptyList();

            List<ImportDataSource> sources = parserService.parseDataSources(new FileReader(metadataPath.resolve("import_datasource_ref.txt").toFile()));
            
            List<NeImportEntity> neEntities = Collections.emptyList();
            List<CounterImportEntity> ceEntities = Collections.emptyList();
            List<AttrImportEntity> aeEntities = Collections.emptyList();
            List<CounterDef> counters = Collections.emptyList();
            List<MocAttributeDef> attributes = Collections.emptyList();
            List<CounterDefGran> granularities = Collections.emptyList();

            if (depth >= 2) {
                neEntities = parserService.parseNeEntities(new FileReader(metadataPath.resolve("ne_import_entity_ref.txt").toFile()));
                ceEntities = parserService.parseCounterEntities(new FileReader(metadataPath.resolve("counter_import_entity_ref.txt").toFile()));
                aeEntities = parserService.parseAttrEntities(new FileReader(metadataPath.resolve("attr_import_entity_ref.txt").toFile()));
            }

            if (depth >= 3) {
                counters = parserService.parseCounters(new FileReader(metadataPath.resolve("counter_def_ref.txt").toFile()));
                attributes = parserService.parseAttributes(new FileReader(metadataPath.resolve("moc_attribute_def_ref.txt").toFile()));
                granularities = parserService.parseCounterGranularities(new FileReader(metadataPath.resolve("counter_def_gran_ref.txt").toFile()));
            }

            return graphService.buildGraph(sources, neEntities, ceEntities, aeEntities, counters, attributes, granularities, depth);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load metadata hierarchy", e);
        }
    }

    @Override
    public Optional<ImportDataSource> getDataSourceById(String username, String timestamp, String adaptorName, String id, int depth) {
        return getDataSourceHierarchy(username, timestamp, adaptorName, depth).stream()
                .filter(ds -> ds.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<MocDef> getMocHierarchy(String username, String timestamp, String adaptorName, int depth) {
        Path metadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        Path nmlPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata/nml").resolve(adaptorName);
        
        try {
            if (!nmlPath.toFile().exists()) return Collections.emptyList();

            List<MocDef> mocs = parserService.parseMocs(new FileReader(nmlPath.resolve("moc_def_ref.txt").toFile()));
            List<MocDefParent> parents = parserService.parseMocParents(new FileReader(nmlPath.resolve("moc_def_parent_ref.txt").toFile()));
            List<VendorMocDef> vendorMocs = parserService.parseVendorMocs(new FileReader(nmlPath.resolve("vendor_moc_def_ref.txt").toFile()));
            
            List<CounterDef> counters = Collections.emptyList();
            List<MocAttributeDef> attributes = Collections.emptyList();
            List<ImportCounterFor> counterMappings = Collections.emptyList();
            List<ImportAttrFor> attributeMappings = Collections.emptyList();
            List<CounterDefGran> granularities = Collections.emptyList();

            if (depth >= 3) {
                counters = parserService.parseCounters(new FileReader(metadataPath.resolve("counter_def_ref.txt").toFile()));
                attributes = parserService.parseAttributes(new FileReader(metadataPath.resolve("moc_attribute_def_ref.txt").toFile()));
                counterMappings = parserService.parseCounterMappings(new FileReader(metadataPath.resolve("import_counter_for_ref.txt").toFile()));
                attributeMappings = parserService.parseAttributeMappings(new FileReader(metadataPath.resolve("import_attr_for_ref.txt").toFile()));
                granularities = parserService.parseCounterGranularities(new FileReader(metadataPath.resolve("counter_def_gran_ref.txt").toFile()));
            }

            return mocHierarchyService.buildTree(mocs, parents, vendorMocs, counters, attributes, counterMappings, attributeMappings, granularities);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load MOC hierarchy", e);
        }
    }

    @Override
    public ProjectContextDTO getProjectContext(String username, String timestamp, String adaptorName) {
        ProjectContextDTO context = new ProjectContextDTO();
        List<ImportDataSource> dataSources = getDataSourceHierarchy(username, timestamp, adaptorName, 3);
        context.setDataSources(dataSources.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
        List<MocDef> mocTree = getMocHierarchy(username, timestamp, adaptorName, 3);
        context.setMocTree(mocTree.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
        Path metadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        try {
            List<CounterDef> counters = parserService.parseCounters(new FileReader(metadataPath.resolve("counter_def_ref.txt").toFile()));
            List<MocAttributeDef> attributes = parserService.parseAttributes(new FileReader(metadataPath.resolve("moc_attribute_def_ref.txt").toFile()));
            List<NeImportEntity> ne = parserService.parseNeEntities(new FileReader(metadataPath.resolve("ne_import_entity_ref.txt").toFile()));
            List<CounterImportEntity> ce = parserService.parseCounterEntities(new FileReader(metadataPath.resolve("counter_import_entity_ref.txt").toFile()));
            List<AttrImportEntity> ae = parserService.parseAttrEntities(new FileReader(metadataPath.resolve("attr_import_entity_ref.txt").toFile()));
            context.setCounters(counters.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setAttributes(attributes.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setNeEntities(ne.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setCounterEntities(ce.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setAttrEntities(ae.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to assemble full context", e);
        }
        return context;
    }

    @Override
    public List<CounterDef> getCountersByEntityId(String username, String timestamp, String adaptorName, String entityId) {
        return getDataSourceHierarchy(username, timestamp, adaptorName, 3).stream()
                .flatMap(ds -> ds.getCounterEntities().stream())
                .filter(ce -> ce.getId().equals(entityId))
                .findFirst()
                .map(CounterImportEntity::getInternalCounters)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<MocAttributeDef> getAttributesByEntityId(String username, String timestamp, String adaptorName, String entityId) {
        return getDataSourceHierarchy(username, timestamp, adaptorName, 3).stream()
                .flatMap(ds -> ds.getAttrEntities().stream())
                .filter(ae -> ae.getId().equals(entityId))
                .findFirst()
                .map(AttrImportEntity::getInternalAttributes)
                .orElse(Collections.emptyList());
    }

    @Override
    public Optional<CounterDef> getCounterById(String username, String timestamp, String adaptorName, String id) {
        Path metadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        try {
            return parserService.parseCounters(new FileReader(metadataPath.resolve("counter_def_ref.txt").toFile())).stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<MocAttributeDef> getAttributeById(String username, String timestamp, String adaptorName, String id) {
        Path metadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        try {
            return parserService.parseAttributes(new FileReader(metadataPath.resolve("moc_attribute_def_ref.txt").toFile())).stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst();
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}
