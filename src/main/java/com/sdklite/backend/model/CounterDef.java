package com.sdklite.backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class CounterDef {
    private String id;
    private String name;
    private String description;
    // Map to capture dynamic fields from CSV
    private Map<String, String> attributes;
}
