package com.sdklite.backend.model;

import lombok.Data;

@Data
public class AttrImportEntity {
    private String id;
    private String dataSourceId;
    private String entityName;
    private String connectionString;
    private boolean isActive;
}
