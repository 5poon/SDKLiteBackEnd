# Track Specification: Data Model & CSV Alignment Audit

## Objective
Ensure 100% field parity between the backend Java models (POJOs/DTOs) and the physical CSV file headers (`*_ref.txt`). This prevents data loss during the import-modify-export cycle and provides the GUI with all necessary technical metadata.

## Core Requirements

### 1. Model Expansion (POJOs)
- **Import Entities:** Update `NeImportEntity`, `CounterImportEntity`, and `AttrImportEntity` to include missing fields like `idf_dyn_import_key_mgr`, `bean_class`, `import_priority`, etc.
- **MOCs:** Update `MocDef` and `VendorMocDef` to include fields like `behaviour`, `flags`, `icon_filename`.
- **Counters & Attributes:** Update `CounterDef` and `MocAttributeDef` to make common technical fields explicit (e.g., `aggregate_type`, `formula`, `integral_type`).

### 2. New Relationship Models
- Implement `CounterDefGran` POJO/DTO mapping to `counter_def_gran_ref.txt`.
- Implement `ImportCounterFor` and `ImportAttrFor` mapping POJOs.

### 3. API & Mapping Sync
- Update all corresponding DTOs to reflect the expanded field sets.
- Update `MetadataMapper` (MapStruct) to ensure all fields are correctly mapped between Entity and DTO.
- Update `MetadataParserService` and `MetadataSerializerService` to handle the new explicit fields instead of relying solely on the dynamic `attributes` map where possible.

## Technical Constraints
- **Naming Conventions:** Use camelCase for Java fields and match them to snake_case CSV headers via MapStruct or manual mapping.
- **Backward Compatibility:** Maintain the dynamic `attributes` map as a fallback for any unforeseen fields.
