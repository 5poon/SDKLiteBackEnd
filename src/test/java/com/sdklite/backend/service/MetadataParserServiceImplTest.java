package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.ImportDataSource;
import com.sdklite.backend.model.NeImportEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetadataParserServiceImplTest {

    private MetadataParserService parserService;

    @BeforeEach
    void setUp() {
        parserService = new MetadataParserServiceImpl();
    }

    @Test
    void parseCounters_ShouldParseCSVCorrectly() throws IOException {
        String csvData = "id,name,description,aggregate_type\n" +
                         "101,CounterA,DescriptionA,SUM\n" +
                         "102,CounterB,DescriptionB,AVG";
        
        List<CounterDef> counters = parserService.parseCounters(new StringReader(csvData));
        
        assertEquals(2, counters.size());
        assertEquals("101", counters.get(0).getId());
        assertEquals("CounterA", counters.get(0).getName());
        assertEquals("SUM", counters.get(0).getAttributes().get("aggregate_type"));
    }

    @Test
    void parseDataSources_ShouldParseCorrectly() throws IOException {
        String csvData = "id,name,bean_class,connection_string,is_active\n" +
                         "1,Source1,com.test.Bean,jdbc:mysql://localhost:3306/db,1\n" +
                         "2,Source2,com.test.Bean2,,0";

        List<ImportDataSource> sources = parserService.parseDataSources(new StringReader(csvData));

        assertEquals(2, sources.size());
        assertEquals("1", sources.get(0).getId());
        assertTrue(sources.get(0).isActive());
        assertFalse(sources.get(1).isActive());
    }

    @Test
    void parseNeEntities_ShouldParseCorrectly() throws IOException {
        String csvData = "id,idf_import_datasource,entity_name,connection_string,is_active\n" +
                         "10,1,NE_Table,table_name,1";

        List<NeImportEntity> entities = parserService.parseNeEntities(new StringReader(csvData));

        assertEquals(1, entities.size());
        assertEquals("10", entities.get(0).getId());
        assertEquals("1", entities.get(0).getDataSourceId());
        assertEquals("NE_Table", entities.get(0).getEntityName());
    }
}
