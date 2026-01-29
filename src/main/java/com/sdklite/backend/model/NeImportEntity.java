package com.sdklite.backend.model;

import lombok.Data;

@Data
public class NeImportEntity {
    private String id;
    private String dynImportKeyMgrId; // idf_dyn_import_key_mgr
    private String dataSourceId;      // idf_import_datasource
    private String beanClass;
    private String connectionString;
    private String entityName;
    private Integer importPriority;
    private boolean isActive;
    private String lastImportDate;
    private Long lastImportLineCount;
    private Integer thresholdReliabilityPercent;
}