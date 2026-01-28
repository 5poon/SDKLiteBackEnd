package com.sdklite.backend.mapper;

import com.sdklite.backend.dto.CounterDefDTO;
import com.sdklite.backend.model.CounterDef;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetadataMapperTest {

    private final MetadataMapper mapper = Mappers.getMapper(MetadataMapper.class);

    @Test
    void toDTO_ShouldMapCorrectly() {
        CounterDef entity = new CounterDef();
        entity.setId("1");
        entity.setName("Counter1");
        Map<String, String> attrs = new HashMap<>();
        attrs.put("key", "value");
        entity.setAttributes(attrs);

        CounterDefDTO dto = mapper.toDTO(entity);

        assertEquals("1", dto.getId());
        assertEquals("Counter1", dto.getName());
        assertEquals("value", dto.getAttributes().get("key"));
    }

    @Test
    void toEntity_ShouldMapCorrectly() {
        CounterDefDTO dto = new CounterDefDTO();
        dto.setId("2");
        dto.setName("Counter2");
        Map<String, String> attrs = new HashMap<>();
        attrs.put("key2", "value2");
        dto.setAttributes(attrs);

        CounterDef entity = mapper.toEntity(dto);

        assertEquals("2", entity.getId());
        assertEquals("Counter2", entity.getName());
        assertEquals("value2", entity.getAttributes().get("key2"));
    }
}
