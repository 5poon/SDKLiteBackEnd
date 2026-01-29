package com.sdklite.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class CounterImportEntityDTO {
    private String id;
    private String entityName;
    private boolean isActive;
    private List<CounterDefDTO> counters;
}