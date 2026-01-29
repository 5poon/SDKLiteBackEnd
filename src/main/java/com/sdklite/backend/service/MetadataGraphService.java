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
            List<MocAttributeDef> attributes,
            List<CounterDefGran> granularities) {

        // Index Data Sources
        Map<String, ImportDataSource> sourceMap = dataSources.stream()
                .collect(Collectors.toMap(ImportDataSource::getId, Function.identity()));

        // Index Counter Entities
        Map<String, CounterImportEntity> ceMap = counterEntities.stream()
                .collect(Collectors.toMap(CounterImportEntity::getId, Function.identity()));

        // Index Attribute Entities
        Map<String, AttrImportEntity> aeMap = attrEntities.stream()
                .collect(Collectors.toMap(AttrImportEntity::getId, Function.identity()));

        // Index Counters
        Map<String, CounterDef> counterMap = counters.stream()
                .collect(Collectors.toMap(CounterDef::getId, Function.identity()));

        // Link NE Entities to Sources
        for (NeImportEntity ne : neEntities) {
            ImportDataSource source = sourceMap.get(ne.getDataSourceId());
            if (source != null) {
                source.getNeEntities().add(ne);
            }
        }

        // Link Counter Entities to Sources
        for (CounterImportEntity ce : counterEntities) {
            ImportDataSource source = sourceMap.get(ce.getDataSourceId());
            if (source != null) {
                source.getCounterEntities().add(ce);
            }
        }

        // Link Attribute Entities to Sources
        for (AttrImportEntity ae : attrEntities) {
            ImportDataSource source = sourceMap.get(ae.getDataSourceId());
            if (source != null) {
                source.getAttrEntities().add(ae);
            }
        }

        // --- NEW LOGIC: Link Counters via Granularity bridge ---
        for (CounterDefGran gran : granularities) {
            CounterDef counter = counterMap.get(gran.getCounterDefId());
            CounterImportEntity ce = ceMap.get(gran.getCounterImportEntityId());
            if (counter != null && ce != null) {
                if (!ce.getInternalCounters().contains(counter)) {
                    ce.getInternalCounters().add(counter);
                }
            }
        }

        // --- NEW LOGIC: Link Attributes to Entities ---
        for (MocAttributeDef attr : attributes) {
            AttrImportEntity ae = aeMap.get(attr.getMappedAttributeId());
            if (ae != null) {
                if (!ae.getInternalAttributes().contains(attr)) {
                    ae.getInternalAttributes().add(attr);
                }
            }
        }

        return dataSources;
    }
}
