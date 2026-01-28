package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MetadataSerializerServiceImplTest {

    private final MetadataSerializerService serializerService = new MetadataSerializerServiceImpl();

    @Test
    void serializeCounters_ShouldGenerateCorrectCsv() throws IOException {
        CounterDef c1 = new CounterDef();
        c1.setId("101");
        c1.setName("CounterOne");
        Map<String, String> attrs = new LinkedHashMap<>(); // LinkedHashMap to preserve order for testing
        attrs.put("id", "101");
        attrs.put("name", "CounterOne");
        attrs.put("extra", "value1");
        c1.setAttributes(attrs);

        StringWriter writer = new StringWriter();
        serializerService.serializeCounters(List.of(c1), writer);

        String output = writer.toString();
        
        // Check for headers
        assertTrue(output.contains("\"id\",\"name\",\"extra\""));
        // Check for data
        assertTrue(output.contains("\"101\",\"CounterOne\",\"value1\""));
    }
}
