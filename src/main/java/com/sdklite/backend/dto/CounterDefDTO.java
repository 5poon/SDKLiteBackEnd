package com.sdklite.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
public class CounterDefDTO {
    @NotBlank(message = "ID is required")
    private String id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String formula;
    private Integer aggregateType;
    private Integer integralType;
    private String rsComment;
    private Map<String, String> attributes;
}