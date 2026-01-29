# Track Specification: Final Integration & Production Hardening

## Objective
Finalize the backend by providing the exact nested data hierarchy requested by the GUI and hardening the application with consistent error handling and data validation. This ensures the application is robust, user-friendly, and provides a complete data context in a single call.

## Core Requirements

### 1. Nested Data Hierarchy (DataSource -> Entities -> Data)
- **DataSources:** Each `ImportDataSourceDTO` must contain lists of `NeImportEntityDTO`, `CounterImportEntityDTO`, and `AttrImportEntityDTO`.
- **Entities:**
    - `CounterImportEntityDTO` must contain its associated `List<CounterDefDTO>`.
    - `AttrImportEntityDTO` must contain its associated `List<MocAttributeDefDTO>`.
- **MOC Tree:** Each `MocDefDTO` node must contain its associated `List<CounterDefDTO>` and `List<MocAttributeDefDTO>`, resolved via the mapping files.

### 2. Global Error Handling
- Implement `@ControllerAdvice` to catch:
    - `IOException`, `SecurityException`, `IllegalStateException`.
    - `IllegalArgumentException` (for invalid IDs).
- Return a structured JSON error response: `{ "error": "Technical message", "status": 4xx/5xx }`.

### 3. Data Validation
- Use JSR-303 (Hibernate Validator) annotations on DTOs.
- Ensure `id` and `name` are present and non-empty for all "Create" and "Update" operations.

## Technical Constraints
- **Performance:** Complex graph building must be optimized. Use Map-based lookups during assembly.
- **DTOs:** Update `MetadataMapper` to handle recursive and nested mappings.
