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

    public MetadataServiceImpl(FileService fileService, 
                               MetadataParserService parserService, 
                               MetadataGraphService graphService,
                               MocHierarchyService mocHierarchyService,
                               MetadataMapper metadataMapper) {
        this.fileService = fileService;
        this.parserService = parserService;
        this.graphService = graphService;
        this.mocHierarchyService = mocHierarchyService;
        this.metadataMapper = metadataMapper;
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
        
        // Context by default returns everything (depth 3)
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
}