package com.sdklite.backend.mapper;

import com.sdklite.backend.dto.CounterDefDTO;
import com.sdklite.backend.dto.MocDefDTO;
import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import com.sdklite.backend.dto.ImportDataSourceDTO;
import com.sdklite.backend.dto.MocAttributeDefDTO;
import com.sdklite.backend.dto.NeImportEntityDTO;
import com.sdklite.backend.model.ImportDataSource;
import com.sdklite.backend.model.MocAttributeDef;
import com.sdklite.backend.model.NeImportEntity;
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

    MocAttributeDefDTO toDTO(MocAttributeDef entity);
    MocAttributeDef toEntity(MocAttributeDefDTO dto);
}
