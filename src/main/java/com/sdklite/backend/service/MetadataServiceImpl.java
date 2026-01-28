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

    public MetadataServiceImpl(FileService fileService, 
                               MetadataParserService parserService, 
                               MetadataGraphService graphService) {
        this.fileService = fileService;
        this.parserService = parserService;
        this.graphService = graphService;
    }

    @Override
    public List<ImportDataSource> getDataSourceHierarchy(String username, String timestamp) {
        Path configPath = fileService.resolveUserTempPath(username, timestamp).resolve("config/metadata");
        // For simplicity in this iteration, we assume we know where the files are based on the structure.
        // In a real scenario, we'd scan for all *_ref.txt files.
        // Let's assume a specific adaptor name for this proof of concept or scan logic.
        
        try {
            // Placeholder: In a real impl, we'd find the adaptor name directory
            // For now, let's assume we are loading from the base metadata folder if it exists.
            Path dsFile = configPath.resolve("nkia_gnodeb_csv_nr25r2/import_datasource_ref.txt");
            if (!dsFile.toFile().exists()) return Collections.emptyList();

            List<ImportDataSource> sources = parserService.parseDataSources(new FileReader(dsFile.toFile()));
            List<NeImportEntity> neEntities = parserService.parseNeEntities(new FileReader(configPath.resolve("nkia_gnodeb_csv_nr25r2/ne_import_entity_ref.txt").toFile()));
            List<CounterImportEntity> ceEntities = parserService.parseCounterEntities(new FileReader(configPath.resolve("nkia_gnodeb_csv_nr25r2/counter_import_entity_ref.txt").toFile()));
            List<AttrImportEntity> aeEntities = parserService.parseAttrEntities(new FileReader(configPath.resolve("nkia_gnodeb_csv_nr25r2/attr_import_entity_ref.txt").toFile()));

            return graphService.buildGraph(sources, neEntities, ceEntities, aeEntities);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load metadata hierarchy", e);
        }
    }
}
