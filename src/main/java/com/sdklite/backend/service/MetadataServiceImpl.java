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

    @Override
    public ProjectContextDTO getProjectContext(String username, String timestamp, String adaptorName) {
        Path baseMetadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata").resolve(adaptorName);
        Path nmlMetadataPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata/nml").resolve(adaptorName);

        try {
            ProjectContextDTO context = new ProjectContextDTO();

            // Load Data Sources and Entities
            List<ImportDataSource> rawSources = parserService.parseDataSources(new FileReader(baseMetadataPath.resolve("import_datasource_ref.txt").toFile()));
            List<NeImportEntity> rawNe = parserService.parseNeEntities(new FileReader(baseMetadataPath.resolve("ne_import_entity_ref.txt").toFile()));
            List<CounterImportEntity> rawCe = parserService.parseCounterEntities(new FileReader(baseMetadataPath.resolve("counter_import_entity_ref.txt").toFile()));
            List<AttrImportEntity> rawAe = parserService.parseAttrEntities(new FileReader(baseMetadataPath.resolve("attr_import_entity_ref.txt").toFile()));

            List<ImportDataSource> linkedSources = graphService.buildGraph(rawSources, rawNe, rawCe, rawAe);
            context.setDataSources(linkedSources.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setNeEntities(rawNe.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setCounterEntities(rawCe.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setAttrEntities(rawAe.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));

            // Load MOCs
            List<MocDef> rawMocs = parserService.parseMocs(new FileReader(nmlMetadataPath.resolve("moc_def_ref.txt").toFile()));
            List<MocDefParent> rawParents = parserService.parseMocParents(new FileReader(nmlMetadataPath.resolve("moc_def_parent_ref.txt").toFile()));
            List<VendorMocDef> rawVendorMocs = parserService.parseVendorMocs(new FileReader(nmlMetadataPath.resolve("vendor_moc_def_ref.txt").toFile()));

            List<MocDef> mocTree = mocHierarchyService.buildTree(rawMocs, rawParents, rawVendorMocs);
            context.setMocTree(mocTree.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));

            // Load Global Counters and Attributes
            List<CounterDef> rawCounters = parserService.parseCounters(new FileReader(baseMetadataPath.resolve("counter_def_ref.txt").toFile()));
            List<MocAttributeDef> rawAttributes = parserService.parseAttributes(new FileReader(baseMetadataPath.resolve("moc_attribute_def_ref.txt").toFile()));

            context.setCounters(rawCounters.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));
            context.setAttributes(rawAttributes.stream().map(metadataMapper::toDTO).collect(Collectors.toList()));

            return context;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load project context", e);
        }
    }
}