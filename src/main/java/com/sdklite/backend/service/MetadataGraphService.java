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
            List<AttrImportEntity> attrEntities) {

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

        // Link Counter Entities
        for (CounterImportEntity ce : counterEntities) {
            ImportDataSource source = sourceMap.get(ce.getDataSourceId());
            if (source != null) {
                source.getCounterEntities().add(ce);
            }
        }

        // Link Attribute Entities
        for (AttrImportEntity ae : attrEntities) {
            ImportDataSource source = sourceMap.get(ae.getDataSourceId());
            if (source != null) {
                source.getAttrEntities().add(ae);
            }
        }

        return dataSources;
    }
}
