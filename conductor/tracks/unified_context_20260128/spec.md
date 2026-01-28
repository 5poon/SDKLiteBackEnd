# Track Specification: Unified Project Context API

## Objective
Implement a high-performance "Bootstrap" API endpoint that returns the complete metadata state for a selected adaptor. This eliminates the need for the GUI to make multiple calls, providing all DataSources, Entities, MOCs, Counters, and Attributes in a single unified JSON payload.

## Core Requirements

### 1. Unified Context DTO
- Create `ProjectContextDTO` which contains:
    - `List<ImportDataSourceDTO> dataSources`
    - `List<NeImportEntityDTO> neEntities`
    - `List<CounterImportEntityDTO> ceEntities`
    - `List<AttrImportEntityDTO> aeEntities`
    - `List<CounterDefDTO> counters`
    - `List<MocAttributeDefDTO> attributes`
    - `List<MocDefDTO> mocTree`

### 2. Full State Loading Service
- Update `MetadataService` to load **all** reference files:
    - `import_datasource_ref.txt`
    - `ne_import_entity_ref.txt`, `counter_import_entity_ref.txt`, `attr_import_entity_ref.txt`
    - `counter_def_ref.txt`, `moc_attribute_def_ref.txt`
    - `moc_def_ref.txt`, `moc_def_parent_ref.txt`, `vendor_moc_def_ref.txt`
- Build the relationships (Graph + Tree) and map to DTOs.

### 3. Bootstrap Endpoint
- **Endpoint:** `GET /api/v1/metadata/context`
- **Params:** `timestamp`, `adaptorName`.
- **Security:** Requires valid user session and adaptor lock.

## Technical Constraints
- **Parallel Loading:** Consider using CompletableFuture to parse multiple CSVs in parallel for better performance if latency is an issue.
- **Memory:** Ensure streaming is used for large CSVs to avoid OOM.
