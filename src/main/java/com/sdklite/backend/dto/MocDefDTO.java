package com.sdklite.backend.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class MocDefDTO {
    private String id;
    private String name;
    private String parentId;
    private Integer behaviour;
    private Integer flags;
    private String iconFilename;
    private String rsComment;
    private Map<String, String> attributes;
    private List<MocDefDTO> children;
}