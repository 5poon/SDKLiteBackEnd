package com.sdklite.backend.dto;

import lombok.Data;

@Data
public class CounterDefGranDTO {
    private String id;
    private String name;
    private String timeGranularity;
    private String collectionTimeGran;
    private Integer importStatus;
}
