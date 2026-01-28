# Implementation Plan - Track: Metadata Relationship Modeling & Navigation API

## Phase 1: Refined Domain Models & DTOs [checkpoint: 7b7f143]
- [x] Task: Define DataSource and Entity POJOs [commit: a3c195b]
    - [x] Create `ImportDataSource`, `NeImportEntity`, `CounterImportEntity`, `AttrImportEntity` POJOs.
    - [x] Update `MocDef` and `VendorMocDef` to include relationship fields (e.g., `List<CounterDef> counters`).
    - [x] Create corresponding DTOs for the API (`DataSourceDTO`, `ImportEntityDTO`).
    - [x] Update `MetadataMapper` to handle the new nested structures.
    - [x] Write Unit Tests for the new Mapper methods.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Refined Domain Models & DTOs' (Protocol in workflow.md)

## Phase 2: Relationship Linking Logic
- [x] Task: Implement Entity Parsing [commit: 8b0da3a]
    - [x] Update `MetadataParserService` to parse `import_datasource_ref.txt` and entity files.
    - [x] Write Unit Tests for parsing these new file types.
- [x] Task: Implement Graph Builder Service [commit: e0b1def]
    - [x] Create `MetadataGraphService`.
    - [x] Implement method `buildGraph(parsedData)` to stitch objects together:
        - [x] Link Entities to DataSources.
        - [x] Link Counters/Attributes to VendorMOCs using mapping files (`import_*_for_ref.txt`).
        - [x] Link VendorMOCs to logical MOCs.
    - [x] Write Unit Tests verifying the graph integrity (e.g., asserting a MOC has the expected counters).
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Relationship Linking Logic' (Protocol in workflow.md)

## Phase 3: Navigation API
- [ ] Task: Implement Navigation Controller
    - [ ] Create `MetadataController`.
    - [ ] Implement `GET /api/v1/metadata/datasources` to return the source hierarchy.
    - [ ] Implement `GET /api/v1/metadata/mocs/{id}` to return MOC details with linked data.
    - [ ] Integrate `AdaptorExtractionService` (or a Session Service) to ensure data is loaded for the user.
    - [ ] Write Integration Tests using `MockMvc` to verify JSON responses.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Navigation API' (Protocol in workflow.md)
