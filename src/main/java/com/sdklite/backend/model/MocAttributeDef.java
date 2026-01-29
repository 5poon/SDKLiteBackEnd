package com.sdklite.backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class MocAttributeDef {
    private String id;
    private String mappedAttributeId; // idf_mapped_attribute
    private Integer behaviour;
    private Integer flags;
    private Integer integralType;
    private String name;
    private String refGsm;
    private String rsComment;
    
    private Map<String, String> attributes;
}