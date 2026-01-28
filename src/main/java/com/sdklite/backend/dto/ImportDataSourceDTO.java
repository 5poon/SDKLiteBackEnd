package com.sdklite.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class ImportDataSourceDTO {
    private String id;
    private String name;
    private boolean isActive;
    private List<NeImportEntityDTO> neEntities;
}
