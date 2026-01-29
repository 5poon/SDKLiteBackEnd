package com.sdklite.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
