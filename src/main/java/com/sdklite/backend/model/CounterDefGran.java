package com.sdklite.backend.model;

import lombok.Data;

@Data
public class CounterDefGran {
    private String id;
    private String counterDefId;        // idf_counter_def
    private String counterImportEntityId; // idf_counter_import_entity
    private String aggregationBeanClass;
    private String importKey;
    private Integer importPriority;
    private String name;
    private String timeGranularity;
    private String collectionTimeGran;
    private Integer importStatus;
}
