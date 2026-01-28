package com.sdklite.backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class MocDef {
    private String id;
    private String name;
    private String parentId;
    private Map<String, String> attributes;
}
