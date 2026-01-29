package com.sdklite.backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class MocAttributeDefDTO {
    private String id;
    private String name;
    private Integer behaviour;
    private Integer flags;
    private Integer integralType;
    private String refGsm;
    private String rsComment;
    private Map<String, String> attributes;
}