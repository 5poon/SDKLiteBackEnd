package com.sdklite.backend.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CounterDefDTO {
    private String id;
    private String name;
    private Map<String, String> attributes;
}
