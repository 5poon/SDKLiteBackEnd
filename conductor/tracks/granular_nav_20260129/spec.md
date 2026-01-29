# Track Specification: Granular Metadata Navigation API

## Objective
Enable fine-grained navigation of the metadata hierarchy by providing specific endpoints for sub-resources and individual entity lookups. This allows the GUI to efficiently query parts of the tree without reloading entire hierarchies.

## Core Requirements

### 1. DataSource-to-Entity Navigation
- **Endpoint:** `GET /api/v1/metadata/datasources/{id}/entities`
- **Result:** Returns all NE, Counter, and Attribute entities associated with the given DataSource ID.

### 2. Entity-to-Item Navigation
- **Endpoint:** `GET /api/v1/metadata/entities/{id}/counters`
- **Endpoint:** `GET /api/v1/metadata/entities/{id}/attributes`
- **Result:** Returns the list of specific data items (CounterDefs or MocAttributeDefs) belonging to the given Entity ID.

### 3. Individual Item Lookups
- **Endpoint:** `GET /api/v1/metadata/counters/{id}`
- **Endpoint:** `GET /api/v1/metadata/attributes/{id}`
- **Result:** Returns a single DTO for the requested item.

## Technical Constraints
- **Consistency:** Use existing `MetadataService` orchestration to ensure data is loaded and linked correctly before filtering.
- **DTOs:** Reuse existing DTOs (`CounterDefDTO`, `MocAttributeDefDTO`, etc.).
