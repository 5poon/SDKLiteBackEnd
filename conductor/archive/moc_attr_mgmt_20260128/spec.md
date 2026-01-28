# Track Specification: MOC Hierarchy & Attribute Management

## Objective
Complete the metadata business logic by implementing full hierarchical MOC loading and support for Attribute management. This ensures the backend can represent the parent-child relationships between network objects and allows the GUI to manage attributes associated with those objects.

## Core Requirements

### 1. Hierarchical MOC Engine
- **Tree Building:** Implement logic to parse `moc_def_ref.txt` and `moc_def_parent_ref.txt` to build a recursive tree of `MocDef` objects.
- **Vendor Mapping:** Link `VendorMocDef` instances to their corresponding logical `MocDef` using the `idf_moc_def` foreign key.
- **Dynamic Adaptor Loading:** Refactor `MetadataService` to accept `adaptorName` as a parameter and resolve paths dynamically.

### 2. Attribute Management (CRUD)
- **Attribute Models:** Define `MocAttributeDef` POJO/DTO.
- **Mapping Glue:** Implement parsing/serialization for `import_attr_for_ref.txt` to associate attributes with specific `VendorMocDef`s.
- **Quota Check:** Attributes creation must be governed by user quotas (e.g., `maxAttributes`).
- **Persistence:** Ensure attribute changes are persisted to the correct `*_ref.txt` files in the user work area.

### 3. Navigation & Detail API
- **Endpoint:** `GET /api/v1/metadata/mocs/tree`
    - Returns the full recursive hierarchy of MOCs.
- **Endpoint:** `POST/PUT/DELETE /api/v1/metadata/attributes`
    - CRUD endpoints for managing attributes.

## Technical Constraints
- **Recursive Logic:** Use recursion or iterative tree building for the MOC hierarchy.
- **Performance:** Cache the built hierarchy per session to avoid expensive tree reconstruction.
