package com.sdklite.backend.service;

import com.sdklite.backend.model.*;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class MetadataServiceImpl implements MetadataService {

    private final FileService fileService;
    private final MetadataParserService parserService;
    private final MetadataGraphService graphService;
    private final MocHierarchyService mocHierarchyService;

    public MetadataServiceImpl(FileService fileService, 
                               MetadataParserService parserService, 
                               MetadataGraphService graphService,
                               MocHierarchyService mocHierarchyService) {
        this.fileService = fileService;
        this.parserService = parserService;
        this.graphService = graphService;
        this.mocHierarchyService = mocHierarchyService;
    }

    @Override
    public List<ImportDataSource> getDataSourceHierarchy(String username, String timestamp, String adaptorName) {
        Path metadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        
        try {
            if (!metadataPath.toFile().exists()) return Collections.emptyList();

            List<ImportDataSource> sources = parserService.parseDataSources(new FileReader(metadataPath.resolve("import_datasource_ref.txt").toFile()));
            List<NeImportEntity> neEntities = parserService.parseNeEntities(new FileReader(metadataPath.resolve("ne_import_entity_ref.txt").toFile()));
            List<CounterImportEntity> ceEntities = parserService.parseCounterEntities(new FileReader(metadataPath.resolve("counter_import_entity_ref.txt").toFile()));
            List<AttrImportEntity> aeEntities = parserService.parseAttrEntities(new FileReader(metadataPath.resolve("attr_import_entity_ref.txt").toFile()));

            return graphService.buildGraph(sources, neEntities, ceEntities, aeEntities);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load metadata hierarchy", e);
        }
    }

    @Override
    public List<MocDef> getMocHierarchy(String username, String timestamp, String adaptorName) {
        Path nmlPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata/nml").resolve(adaptorName);
        
        try {
            if (!nmlPath.toFile().exists()) return Collections.emptyList();

            List<MocDef> mocs = parserService.parseMocs(new FileReader(nmlPath.resolve("moc_def_ref.txt").toFile()));
            List<MocDefParent> parents = parserService.parseMocParents(new FileReader(nmlPath.resolve("moc_def_parent_ref.txt").toFile()));
            List<VendorMocDef> vendorMocs = parserService.parseVendorMocs(new FileReader(nmlPath.resolve("vendor_moc_def_ref.txt").toFile()));

            return mocHierarchyService.buildTree(mocs, parents, vendorMocs);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load MOC hierarchy", e);
        }
    }
}