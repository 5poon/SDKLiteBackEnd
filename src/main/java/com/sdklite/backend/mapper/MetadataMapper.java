package com.sdklite.backend.mapper;

import com.sdklite.backend.dto.*;
import com.sdklite.backend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MetadataMapper {

    @Mapping(target = "attributes", source = "attributes")
    CounterDefDTO toDTO(CounterDef entity);
    CounterDef toEntity(CounterDefDTO dto);

    @Mapping(target = "mocAttributes", source = "mocAttributeDefs")
    @Mapping(target = "counters", source = "counters")
    MocDefDTO toDTO(MocDef entity);
    MocDef toEntity(MocDefDTO dto);

    ImportDataSourceDTO toDTO(ImportDataSource entity);
    
    NeImportEntityDTO toDTO(NeImportEntity entity);
    
    @Mapping(target = "counters", source = "internalCounters")
    CounterImportEntityDTO toDTO(CounterImportEntity entity);
    
    @Mapping(target = "attributes", source = "internalAttributes")
    AttrImportEntityDTO toDTO(AttrImportEntity entity);

    @Mapping(target = "attributes", source = "attributes")
    MocAttributeDefDTO toDTO(MocAttributeDef entity);
    MocAttributeDef toEntity(MocAttributeDefDTO dto);
    
    CounterDefGranDTO toDTO(CounterDefGran entity);
}
