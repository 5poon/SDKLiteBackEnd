package com.sdklite.backend.service;

import com.sdklite.backend.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetadataGraphServiceTest {

    private final MetadataGraphService graphService = new MetadataGraphService();

    @Test
    void buildGraph_ShouldLinkEntitiesToDataSources() {
        // Setup Sources
        ImportDataSource src1 = new ImportDataSource();
        src1.setId("1");
        List<ImportDataSource> sources = List.of(src1);

        // Setup Entities
        NeImportEntity ne1 = new NeImportEntity();
        ne1.setId("101");
        ne1.setDataSourceId("1");

        CounterImportEntity ce1 = new CounterImportEntity();
        ce1.setId("201");
        ce1.setDataSourceId("1");

        AttrImportEntity ae1 = new AttrImportEntity();
        ae1.setId("301");
        ae1.setDataSourceId("1");

        // Build Graph
        List<ImportDataSource> result = graphService.buildGraph(
                sources,
                List.of(ne1),
                List.of(ce1),
                List.of(ae1)
        );

        // Verify
        assertEquals(1, result.size());
        ImportDataSource resSrc = result.get(0);
        
        assertEquals(1, resSrc.getNeEntities().size());
        assertEquals("101", resSrc.getNeEntities().get(0).getId());

        assertEquals(1, resSrc.getCounterEntities().size());
        assertEquals("201", resSrc.getCounterEntities().get(0).getId());

        assertEquals(1, resSrc.getAttrEntities().size());
        assertEquals("301", resSrc.getAttrEntities().get(0).getId());
    }
}
