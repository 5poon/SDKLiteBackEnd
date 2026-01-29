package com.sdklite.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDataSource {
    private String id;
    private String name;
    private String beanClass;
    private String connectionString;
    private boolean isActive;
    
    private List<NeImportEntity> neEntities = new ArrayList<>();
    private List<CounterImportEntity> counterEntities = new ArrayList<>();
    private List<AttrImportEntity> attrEntities = new ArrayList<>();
}