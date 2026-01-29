package com.sdklite.backend.dto;

import lombok.Data;

@Data
public class CounterImportEntityDTO {
    private String id;
    private String entityName;
    private boolean isActive;
}
