package com.sdklite.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Map;

@Data
public class MocAttributeDefDTO {
    @NotBlank(message = "ID is required")
    private String id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private Integer behaviour;
    private Integer flags;
    private Integer integralType;
    private String refGsm;
    private String rsComment;
    private Map<String, String> attributes;
}