package com.sdklite.backend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AttrImportEntity {
    private String id;
    private String dynImportKeyMgrId; // idf_dyn_import_key_mgr
    private String dataSourceId;      // idf_import_datasource
    private String neListSelectorId;  // idf_ne_list_selector
    private String beanClass;
    private String connectionString;
    private String entityName;
    private boolean isActive;
    private String lastImportDate;
    private Long lastImportLineCount;
    private Integer thresholdReliabilityPercent;
    
    // Relationship
    private List<MocAttributeDef> internalAttributes = new ArrayList<>();
}