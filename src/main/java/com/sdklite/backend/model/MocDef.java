package com.sdklite.backend.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class MocDef {
    private String id;
    private String name;
    private String parentId;
    private Integer behaviour;
    private Integer flags;
    private String iconFilename;
    private String rsComment;
    private Map<String, String> attributes;
    
    // Relationships
    private List<VendorMocDef> vendorMocDefs = new ArrayList<>();
    private List<MocDef> children = new ArrayList<>();
}