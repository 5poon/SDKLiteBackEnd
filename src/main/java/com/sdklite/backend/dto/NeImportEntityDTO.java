package com.sdklite.backend.dto;

import lombok.Data;

@Data
public class NeImportEntityDTO {
    private String id;
    private String entityName;
    private boolean isActive;
    private String dataSourceId;
    private Integer importPriority;
    private String lastImportDate;
    private Long lastImportLineCount;
}