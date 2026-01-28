package com.sdklite.backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class MocAttributeDef {
    private String id;
    private String name;
    private Map<String, String> attributes;
}
