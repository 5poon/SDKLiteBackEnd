package com.sdklite.backend.mapper;

import com.sdklite.backend.dto.ImportDataSourceDTO;
import com.sdklite.backend.model.ImportDataSource;
import com.sdklite.backend.model.NeImportEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MetadataMapperDeepTest {

    private final MetadataMapper mapper = Mappers.getMapper(MetadataMapper.class);

    @Test
    void toDTO_ShouldMapDataSourceWithNestedEntities() {
        ImportDataSource source = new ImportDataSource();
        source.setId("src1");
        source.setName("Source 1");
        source.setActive(true);

        NeImportEntity ne = new NeImportEntity();
        ne.setId("ne1");
        ne.setEntityName("NE 1");
        source.setNeEntities(List.of(ne));

        ImportDataSourceDTO dto = mapper.toDTO(source);

        assertEquals("src1", dto.getId());
        assertEquals("Source 1", dto.getName());
        assertNotNull(dto.getNeEntities());
        assertEquals(1, dto.getNeEntities().size());
        assertEquals("NE 1", dto.getNeEntities().get(0).getEntityName());
    }
}
