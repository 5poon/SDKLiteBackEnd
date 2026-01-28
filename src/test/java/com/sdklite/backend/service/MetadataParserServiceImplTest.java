package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
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
}
