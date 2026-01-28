package com.sdklite.backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class MocAttributeDefDTO {
    private String id;
    private String name;
    private Map<String, String> attributes;
}
