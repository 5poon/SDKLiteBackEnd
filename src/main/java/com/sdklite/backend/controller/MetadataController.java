package com.sdklite.backend.controller;

import com.sdklite.backend.dto.*;
import com.sdklite.backend.mapper.MetadataMapper;
import com.sdklite.backend.service.MetadataCrudService;
import com.sdklite.backend.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/metadata")
@Tag(name = "Metadata", description = "Operations for adaptor metadata and hierarchies")
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
    @Operation(summary = "Get Data Source Hierarchy", description = "Returns the DataSource -> Entity nested hierarchy for a specific adaptor.")
    public List<ImportDataSourceDTO> getDataSourceHierarchy(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            Principal principal) {
        
        String username = principal.getName();
        return metadataService.getDataSourceHierarchy(username, timestamp, adaptorName).stream()
                .map(metadataMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/mocs/tree")
    @Operation(summary = "Get MOC Tree", description = "Returns the recursive hierarchy of Managed Object Classes (MOCs).")
    public List<MocDefDTO> getMocHierarchy(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            Principal principal) {
        
        String username = principal.getName();
        return metadataService.getMocHierarchy(username, timestamp, adaptorName).stream()
                .map(metadataMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/context")
    @Operation(summary = "Get Full Project Context", description = "Bootstrap endpoint returning all metadata (sources, entities, mocs, counters, attributes) in one payload.")
    public ProjectContextDTO getProjectContext(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            Principal principal) {
        
        String username = principal.getName();
        return metadataService.getProjectContext(username, timestamp, adaptorName);
    }

    @PostMapping("/counters")
    @Operation(summary = "Create Counter", description = "Adds a new counter definition to the work area.")
    public List<CounterDefDTO> createCounter(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            @RequestBody CounterDefDTO counterDTO,
            Principal principal) throws IOException {
        
        return metadataCrudService.createCounter(principal.getName(), timestamp, adaptorName, metadataMapper.toEntity(counterDTO))
                .stream().map(metadataMapper::toDTO).collect(Collectors.toList());
    }

    @PutMapping("/counters/{id}")
    @Operation(summary = "Update Counter", description = "Updates an existing counter definition.")
    public List<CounterDefDTO> updateCounter(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            @PathVariable String id,
            @RequestBody CounterDefDTO counterDTO,
            Principal principal) throws IOException {
        
        return metadataCrudService.updateCounter(principal.getName(), timestamp, adaptorName, id, metadataMapper.toEntity(counterDTO))
                .stream().map(metadataMapper::toDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/counters/{id}")
    @Operation(summary = "Delete Counter", description = "Removes a counter definition.")
    public List<CounterDefDTO> deleteCounter(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            @PathVariable String id,
            Principal principal) throws IOException {
        
        return metadataCrudService.deleteCounter(principal.getName(), timestamp, adaptorName, id)
                .stream().map(metadataMapper::toDTO).collect(Collectors.toList());
    }

    @PostMapping("/attributes")
    @Operation(summary = "Create Attribute", description = "Adds a new attribute definition.")
    public void createAttribute(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            @RequestBody MocAttributeDefDTO attributeDTO,
            Principal principal) throws IOException {
        
        metadataCrudService.createAttribute(principal.getName(), timestamp, adaptorName, metadataMapper.toEntity(attributeDTO));
    }

    @PutMapping("/attributes/{id}")
    @Operation(summary = "Update Attribute", description = "Updates an existing attribute definition.")
    public void updateAttribute(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            @PathVariable String id,
            @RequestBody MocAttributeDefDTO attributeDTO,
            Principal principal) throws IOException {
        
        metadataCrudService.updateAttribute(principal.getName(), timestamp, adaptorName, id, metadataMapper.toEntity(attributeDTO));
    }

    @DeleteMapping("/attributes/{id}")
    @Operation(summary = "Delete Attribute", description = "Removes an attribute definition.")
    public void deleteAttribute(
            @Parameter(description = "Session timestamp") @RequestParam String timestamp,
            @Parameter(description = "Adaptor folder name") @RequestParam String adaptorName,
            @PathVariable String id,
            Principal principal) throws IOException {
        
        metadataCrudService.deleteAttribute(principal.getName(), timestamp, adaptorName, id);
    }
}