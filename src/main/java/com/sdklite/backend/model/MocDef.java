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
public class MocDef {
    private String id;
    private String name;
    private String parentId;
    private Integer behaviour;
    private Integer flags;
    private String iconFilename;
    private String rsComment;
    
    private Map<String, String> attributes;
    private List<VendorMocDef> vendorMocDefs = new ArrayList<>();
    private List<MocDef> children = new ArrayList<>();
    private List<MocAttributeDef> mocAttributeDefs = new ArrayList<>();
    private List<CounterDef> counters = new ArrayList<>();
}
