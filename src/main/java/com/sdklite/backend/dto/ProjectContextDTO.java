package com.sdklite.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectContextDTO {
    private List<ImportDataSourceDTO> dataSources;
    private List<NeImportEntityDTO> neEntities;
    private List<CounterImportEntityDTO> counterEntities;
    private List<AttrImportEntityDTO> attrEntities;
    private List<CounterDefDTO> counters;
    private List<MocAttributeDefDTO> attributes;
    private List<MocDefDTO> mocTree;
}
