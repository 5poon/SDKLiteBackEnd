# Track Specification: CRUD Operations & Publish Lifecycle

## Objective
Implement the complete lifecycle for modifying adaptor metadata. This includes creating, updating, and deleting metadata entries (Counters, Attributes) via REST API, ensuring changes are saved to the user's temporary directory, and providing a "Publish" mechanism to package the modifications into a new versioned `.iar` file.

## Core Requirements

### 1. Metadata Serialization & Persistence
- **Serialization:** Implement logic to convert Domain POJOs (`CounterDef`, `MocDef`, etc.) back into CSV format string compatible with the `*_ref.txt` files.
- **Persistence:** Use the `FileService` to perform atomic writes of the serialized CSV content to the user's `config/metadata` directory.
- **Auto-Save:** Any CRUD operation must trigger a rewrite of the corresponding file to ensure the temporary state is always up-to-date.

### 2. CRUD REST API
- **Endpoint:** `POST /api/v1/metadata/{type}` (Create)
- **Endpoint:** `PUT /api/v1/metadata/{type}/{id}` (Update)
- **Endpoint:** `DELETE /api/v1/metadata/{type}/{id}` (Delete)
- **Validation:**
    - Enforce User Quotas (e.g., maximum number of counters) before allowing a "Create" operation.
    - Validate that the target ID exists for "Update" and "Delete".
- **Consistency:** Ensure that deleting a `VendorMocDef` also handles associated mappings if necessary (to be refined in implementation).

### 3. Publish Service
- **Repackaging:** Compress the entire `config` directory from the user's temporary folder into a new `.iar` (ZIP) file.
- **Versioning:** 
    - The user should provide a new version string, or the system should suggest an incremented version (e.g., `03.01.01` -> `03.01.02`).
- **Finalization:** Move the generated `.iar` file to the global `example/adaptors/<vendor>/<tech>/<name>/<version>/` directory.
- **Session Cleanup:** After successful publishing, release any locks held by the user.

## Technical Constraints
- **Concurrency:** Rely on the `LockService` implemented in previous tracks to ensure only the lock holder can perform CRUD and Publish operations on a specific adaptor.
- **Testing:** Unit tests for serialization and integration tests for the full CRUD-to-file flow.
