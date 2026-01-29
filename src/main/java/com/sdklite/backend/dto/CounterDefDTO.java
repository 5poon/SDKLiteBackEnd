package com.sdklite.backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CounterDefDTO {
    private String id;
    private String name;
    private String formula;
    private Integer aggregateType;
    private Integer integralType;
    private String rsComment;
    private Map<String, String> attributes;
}