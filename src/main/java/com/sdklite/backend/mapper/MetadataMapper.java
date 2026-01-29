package com.sdklite.backend.mapper;

import com.sdklite.backend.dto.CounterDefDTO;
import com.sdklite.backend.dto.MocDefDTO;
import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import com.sdklite.backend.dto.*;
import com.sdklite.backend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MetadataMapper {

    CounterDefDTO toDTO(CounterDef entity);
    CounterDef toEntity(CounterDefDTO dto);

    MocDefDTO toDTO(MocDef entity);
    MocDef toEntity(MocDefDTO dto);

    ImportDataSourceDTO toDTO(ImportDataSource entity);
    NeImportEntityDTO toDTO(NeImportEntity entity);
    CounterImportEntityDTO toDTO(CounterImportEntity entity);
    AttrImportEntityDTO toDTO(AttrImportEntity entity);

    MocAttributeDefDTO toDTO(MocAttributeDef entity);
    MocAttributeDef toEntity(MocAttributeDefDTO dto);
}
