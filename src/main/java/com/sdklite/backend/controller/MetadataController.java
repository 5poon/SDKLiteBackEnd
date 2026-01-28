package com.sdklite.backend.controller;

import com.sdklite.backend.dto.ImportDataSourceDTO;
import com.sdklite.backend.mapper.MetadataMapper;
import com.sdklite.backend.service.MetadataService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/metadata")
public class MetadataController {

    private final MetadataService metadataService;
    private final MetadataMapper metadataMapper;

    public MetadataController(MetadataService metadataService, MetadataMapper metadataMapper) {
        this.metadataService = metadataService;
        this.metadataMapper = metadataMapper;
    }

    @GetMapping("/datasources")
    public List<ImportDataSourceDTO> getDataSourceHierarchy(
            @RequestParam String timestamp,
            Principal principal) {
        
        String username = principal.getName();
        return metadataService.getDataSourceHierarchy(username, timestamp).stream()
                .map(metadataMapper::toDTO)
                .collect(Collectors.toList());
    }
}
