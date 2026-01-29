package com.sdklite.backend.service;

import com.sdklite.backend.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MetadataGraphService {

    public List<ImportDataSource> buildGraph(
            List<ImportDataSource> dataSources,
            List<NeImportEntity> neEntities,
            List<CounterImportEntity> counterEntities,
            List<AttrImportEntity> attrEntities,
            List<CounterDef> counters,
            List<MocAttributeDef> attributes) {

        // Index Data Sources
        Map<String, ImportDataSource> sourceMap = dataSources.stream()
                .collect(Collectors.toMap(ImportDataSource::getId, Function.identity()));

        // Link NE Entities
        for (NeImportEntity ne : neEntities) {
            ImportDataSource source = sourceMap.get(ne.getDataSourceId());
            if (source != null) {
                source.getNeEntities().add(ne);
            }
        }

        // Index Counter Entities
        Map<String, CounterImportEntity> ceMap = counterEntities.stream()
                .collect(Collectors.toMap(CounterImportEntity::getId, Function.identity()));

        // Link Counters to Entities
        for (CounterDef counter : counters) {
            CounterImportEntity ce = ceMap.get(counter.getImportEntityId());
            if (ce != null) {
                ce.getInternalCounters().add(counter); // Note: I need to add this list to the POJO
            }
        }

        // Link Counter Entities to Sources
        for (CounterImportEntity ce : counterEntities) {
            ImportDataSource source = sourceMap.get(ce.getDataSourceId());
            if (source != null) {
                source.getCounterEntities().add(ce);
            }
        }

        // Index Attribute Entities
        Map<String, AttrImportEntity> aeMap = attrEntities.stream()
                .collect(Collectors.toMap(AttrImportEntity::getId, Function.identity()));

        // Link Attributes to Entities
        for (MocAttributeDef attr : attributes) {
            AttrImportEntity ae = aeMap.get(attr.getMappedAttributeId()); // Re-using idf_mapped_attribute as link to AE
            if (ae != null) {
                ae.getInternalAttributes().add(attr); // Note: I need to add this list to the POJO
            }
        }

        // Link Attribute Entities to Sources
        for (AttrImportEntity ae : attrEntities) {
            ImportDataSource source = sourceMap.get(ae.getDataSourceId());
            if (source != null) {
                source.getAttrEntities().add(ae);
            }
        }

        return dataSources;
    }
}