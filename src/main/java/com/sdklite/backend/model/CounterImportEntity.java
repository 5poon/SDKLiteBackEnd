package com.sdklite.backend.model;

import lombok.Data;

@Data
public class CounterImportEntity {
    private String id;
    private String dataSourceId;
    private String neListSelectorId; // Optional relationship
    private String entityName;
    private String connectionString;
    private boolean isActive;
}
