package com.sdklite.backend.model;

import lombok.Data;
import java.util.Map;

@Data
public class CounterDef {
    private String id;
    private String causeSetId;      // idf_counter_cause_set
    private Integer collectionType;  // idf_counter_coll_type
    private Integer dataType;        // idf_counter_data_type
    private String groupId;         // idf_counter_group
    private String mappedCounterId;  // idf_mapped_counter
    private Integer aggregateType;
    private String formula;
    private String name;
    private String rsComment;
    private String isBhOf;          // idf_is_bh_of
    private String isBhValueOf;     // idf_is_bhvalue_of
    private String isBhValueAt;     // idf_is_bhvalue_at
    private String bhType;
    private Integer integralType;
    
    // Map to capture dynamic fields from CSV as fallback
    private Map<String, String> attributes;
    
    // Relationships
    private String importEntityId; // Link to CounterImportEntity
    private CounterImportEntity importEntity;
}