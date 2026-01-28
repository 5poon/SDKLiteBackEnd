# Implementation Plan - Track: Metadata Relationship Modeling & Navigation API

## Phase 1: Refined Domain Models & DTOs
- [ ] Task: Define DataSource and Entity POJOs
    - [ ] Create `ImportDataSource`, `NeImportEntity`, `CounterImportEntity`, `AttrImportEntity` POJOs.
    - [ ] Update `MocDef` and `VendorMocDef` to include relationship fields (e.g., `List<CounterDef> counters`).
    - [ ] Create corresponding DTOs for the API (`DataSourceDTO`, `ImportEntityDTO`).
    - [ ] Update `MetadataMapper` to handle the new nested structures.
    - [ ] Write Unit Tests for the new Mapper methods.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Refined Domain Models & DTOs' (Protocol in workflow.md)

## Phase 2: Relationship Linking Logic
- [ ] Task: Implement Entity Parsing
    - [ ] Update `MetadataParserService` to parse `import_datasource_ref.txt` and entity files.
    - [ ] Write Unit Tests for parsing these new file types.
- [ ] Task: Implement Graph Builder Service
    - [ ] Create `MetadataGraphService`.
    - [ ] Implement method `buildGraph(parsedData)` to stitch objects together:
        - [ ] Link Entities to DataSources.
        - [ ] Link Counters/Attributes to VendorMOCs using mapping files (`import_*_for_ref.txt`).
        - [ ] Link VendorMOCs to logical MOCs.
    - [ ] Write Unit Tests verifying the graph integrity (e.g., asserting a MOC has the expected counters).
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Relationship Linking Logic' (Protocol in workflow.md)

## Phase 3: Navigation API
- [ ] Task: Implement Navigation Controller
    - [ ] Create `MetadataController`.
    - [ ] Implement `GET /api/v1/metadata/datasources` to return the source hierarchy.
    - [ ] Implement `GET /api/v1/metadata/mocs/{id}` to return MOC details with linked data.
    - [ ] Integrate `AdaptorExtractionService` (or a Session Service) to ensure data is loaded for the user.
    - [ ] Write Integration Tests using `MockMvc` to verify JSON responses.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Navigation API' (Protocol in workflow.md)
