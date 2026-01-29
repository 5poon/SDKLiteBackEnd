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
    
    private Map<String, String> attributes;
    private String importEntityId; 
}
