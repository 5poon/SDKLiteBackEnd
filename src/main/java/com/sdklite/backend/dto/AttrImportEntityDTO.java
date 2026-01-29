package com.sdklite.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class AttrImportEntityDTO {
    private String id;
    private String entityName;
    private boolean isActive;
    private List<MocAttributeDefDTO> attributes;
}