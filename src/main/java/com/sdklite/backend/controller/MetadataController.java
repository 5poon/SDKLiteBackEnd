package com.sdklite.backend.controller;

import com.sdklite.backend.dto.CounterDefDTO;
import com.sdklite.backend.dto.ImportDataSourceDTO;
import com.sdklite.backend.mapper.MetadataMapper;
import com.sdklite.backend.service.MetadataCrudService;
import com.sdklite.backend.service.MetadataService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/metadata")
public class MetadataController {

    private final MetadataService metadataService;
    private final MetadataCrudService metadataCrudService;
    private final MetadataMapper metadataMapper;

    public MetadataController(MetadataService metadataService, 
                              MetadataCrudService metadataCrudService,
                              MetadataMapper metadataMapper) {
        this.metadataService = metadataService;
        this.metadataCrudService = metadataCrudService;
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

    @PostMapping("/counters")
    public List<CounterDefDTO> createCounter(
            @RequestParam String timestamp,
            @RequestParam String adaptorName,
            @RequestBody CounterDefDTO counterDTO,
            Principal principal) throws IOException {
        
        return metadataCrudService.createCounter(principal.getName(), timestamp, adaptorName, metadataMapper.toEntity(counterDTO))
                .stream().map(metadataMapper::toDTO).collect(Collectors.toList());
    }

    @PutMapping("/counters/{id}")
    public List<CounterDefDTO> updateCounter(
            @RequestParam String timestamp,
            @RequestParam String adaptorName,
            @PathVariable String id,
            @RequestBody CounterDefDTO counterDTO,
            Principal principal) throws IOException {
        
        return metadataCrudService.updateCounter(principal.getName(), timestamp, adaptorName, id, metadataMapper.toEntity(counterDTO))
                .stream().map(metadataMapper::toDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/counters/{id}")
    public List<CounterDefDTO> deleteCounter(
            @RequestParam String timestamp,
            @RequestParam String adaptorName,
            @PathVariable String id,
            Principal principal) throws IOException {
        
        return metadataCrudService.deleteCounter(principal.getName(), timestamp, adaptorName, id)
                .stream().map(metadataMapper::toDTO).collect(Collectors.toList());
    }
}
