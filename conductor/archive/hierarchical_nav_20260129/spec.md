# Track Specification: Hierarchical Data Navigation API

## Objective
Provide a flexible API for the GUI to navigate the 3-level metadata hierarchy with configurable depth. This allows for optimized data loading (e.g., loading only top-level data sources first, then expanding entities and data points on demand).

## Core Requirements

### 1. 3-Level Data Hierarchy
- **Level 1 (Root):** `DataSource`
- **Level 2 (Entities):** `NeImportEntity`, `AttrImportEntity`, `CounterImportEntity`.
- **Level 3 (Data Items):** `MocAttributeDef`, `CounterDef`.

### 2. Flexible Depth Support
- The API must support a `depth` query parameter (1, 2, or 3):
    - `depth=1`: Returns only the root object (e.g., DataSource fields only).
    - `depth=2`: Returns root + direct children (e.g., DataSource + Entities).
    - `depth=3`: Returns full hierarchy (e.g., DataSource + Entities + Data Items).

### 3. Navigation API Endpoints
- **DataSource Retrieval:**
    - `GET /api/v1/datasources`: List all DataSources with configurable depth.
    - `GET /api/v1/datasources/{id}`: Get specific DataSource with configurable depth.
- **Entity Retrieval:**
    - `GET /api/v1/entities/{id}`: Get specific Entity (NE/AE/CE) with configurable depth (Level 2 or Level 2+3).

## Technical Constraints
- **DTOs:** Use multiple DTO variants or dynamic mapping to avoid sending empty lists when lower depth is requested.
- **Service Layer:** Implement "Depth-Aware" methods that selectively invoke linking logic based on the requested level.
