package com.sdklite.backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class NeImportEntity {
    private String id;
    private String dataSourceId;
    private String entityName;
    private String connectionString;
    private boolean isActive;
    // ... other fields from CSV if needed
}
