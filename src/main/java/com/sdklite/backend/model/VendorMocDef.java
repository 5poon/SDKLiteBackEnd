package com.sdklite.backend.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class VendorMocDef {
    private String id;
    private String mocDefId; // idf_moc_def
    private String vendorId; // idf_vendor
    private String name;
    private Integer behaviour;
    private String iconFilename;
    private String rsComment;
    
    // Relationships
    private List<CounterDef> counters = new ArrayList<>();
}