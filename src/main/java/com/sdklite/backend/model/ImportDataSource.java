package com.sdklite.backend.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ImportDataSource {
    private String id;
    private String name;
    private String beanClass;
    private String connectionString;
    private boolean isActive;
    
    // Relationships
    private List<NeImportEntity> neEntities = new ArrayList<>();
    private List<CounterImportEntity> counterEntities = new ArrayList<>();
    private List<AttrImportEntity> attrEntities = new ArrayList<>();
}
