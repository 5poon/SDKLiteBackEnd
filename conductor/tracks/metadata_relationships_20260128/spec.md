# Track Specification: Metadata Relationship Modeling & Navigation API

## Objective
Update the backend data model to support a rich, nested hierarchy: `DataSource` -> `ImportEntity` (NE/CE/AE) -> `Attributes` / `Counters`. Implement the relationship logic to link these entities based on the CSV reference keys and expose this structure via a RESTful Navigation API.

## Core Requirements

### 1. Refined Data Model (POJOs & DTOs)
- **Hierarchy:**
    - `DataSource`: Root of the import (e.g., a specific DB connection or File source).
    - `ImportEntity`: Represents a table/file being imported. Types:
        - `NE Entity`: Network Element source.
        - `AE Entity`: Attribute source.
        - `CE Entity`: Counter source.
- **Relationships:**
    - `MocDef` must link to its `VendorMocDef`.
    - `VendorMocDef` must contain lists of available `Counters` and `Attributes`.
    - `Counters` and `Attributes` must link back to their originating `ImportEntity` and `DataSource`.

### 2. Relationship Building Service
- **Logic:**
    - Parse `import_datasource_ref.txt` to identify Data Sources.
    - Parse `*_import_entity_ref.txt` to identify Entities and link them to Data Sources.
    - Use `import_counter_for_ref.txt` to link `CounterDefGran` to `VendorMocDef`.
    - Use `import_attr_for_ref.txt` to link `Attributes` to `VendorMocDef`.
    - **Graph Construction:** Post-process the parsed lists to build the object graph (Parent-Child links).

### 3. Navigation API
- **Endpoint:** `GET /api/v1/metadata/tree`
    - Returns the full `DataSource` -> `Entity` hierarchy.
- **Endpoint:** `GET /api/v1/metadata/mocs/{id}`
    - Returns details for a specific MOC, including its *resolved* Counters and Attributes.
- **Endpoint:** `GET /api/v1/metadata/mocs/{id}/counters`
    - Returns specific counter definitions valid for this MOC.

## Technical Constraints
- **Performance:** Building the graph should be done once per session load (or lazily) to avoid re-parsing CSVs on every request.
- **DTOs:** Use `MapStruct` to create clean, nested DTOs for the API response, hiding internal ID references.
