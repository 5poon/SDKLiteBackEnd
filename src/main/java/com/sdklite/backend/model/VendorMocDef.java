package com.sdklite.backend.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class VendorMocDef {
    private String id;
    private String mocDefId; // Link to logical MocDef
    private String vendorId;
    private String name;
    
    // Relationships
    private List<CounterDef> counters = new ArrayList<>();
    // private List<MocAttributeDef> attributes = new ArrayList<>(); // To be added later
}
