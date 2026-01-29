# Track Specification: Advanced Metadata Graph Linking

## Objective
Correct the metadata relationship model to reflect the real CSV dependencies. Specifically, Counters are not linked directly to Import Entities; they are linked via a `CounterDefGran` bridge. Attributes must be correctly mapped to their Import Entities using the explicit mapping field.

## Core Requirements

### 1. Granularity-Based Counter Linking
- **Logic:** 
    - `CounterDef` <--- (idf_counter_def) --- `CounterDefGran` --- (idf_counter_import_entity) ---> `CounterImportEntity`.
- **Implementation:** Update `MetadataGraphService` to iterate through all granularities and bind the corresponding `CounterDef` to the correct `CounterImportEntity`.

### 2. Attribute Mapping
- **Logic:**
    - `MocAttributeDef` --- (idf_mapped_attribute) ---> `AttrImportEntity`.
- **Implementation:** Update `MetadataGraphService` to use the `idf_mapped_attribute` field from `MocAttributeDef` to locate and link the parent `AttrImportEntity`.

### 3. Orchestration Refinement
- Update `MetadataServiceImpl.getDataSourceHierarchy` to load `counter_def_gran_ref.txt` and pass it to the graph builder.

## Technical Constraints
- **Deduplication:** Ensure a `CounterDef` is only added once to an entity's list even if it has multiple granularities.
- **Null Safety:** Handle cases where technical IDs might be missing or invalid in the CSV data.
