# Track Specification: Initial Core Services & Foundation

## Objective
Establish the foundational architecture of the SDKLiteBackEnd application by implementing the core service components required for authentication, file management, adaptor handling, and metadata processing. This track establishes the project skeleton and the primary business logic layers.

## Core Requirements

### 1. Project Initialization
- Initialize a Maven-based Spring Boot 3.x project with Java 17.
- Configure dependencies: Spring Security, Jackson, Apache Commons Compress, Apache Commons CSV, Lombok, MapStruct.
- Set up the directory structure adhering to standard Spring Boot conventions.

### 2. Authentication Service (Security)
- **Goal:** Secure the application using a file-based user store.
- **Input:** `users.json` or `users.txt` (simulated or real file).
- **Logic:**
    - Load user credentials (hashed passwords) and roles/quotas from the file.
    - Integrate with Spring Security `UserDetailsService`.
    - Enforce RBAC based on the loaded roles.
- **Output:** Authenticated session context.

### 3. File Management Service
- **Goal:** Handle all file I/O operations safely and atomically.
- **Logic:**
    - Manage the `example/user_temp/<username>/<timestamp>/` directory structure.
    - Implement atomic write operations (write-to-temp + rename) for data integrity.
    - Validate all file paths to prevent traversal attacks.
    - Provide utility methods for reading/writing text and binary files.

### 4. Locking Service
- **Goal:** Manage concurrent access to adaptors.
- **Logic:**
    - Maintain an in-memory or file-based registry of active locks.
    - Key: `adaptor_id` (vendor/tech/name/version).
    - Value: `username`, `timestamp`.
    - API: `acquireLock(adaptor, user)`, `releaseLock(adaptor, user)`, `checkLock(adaptor)`.

### 5. Adaptor Service
- **Goal:** Manage the lifecycle of adaptors (List, Load/Extract).
- **Logic:**
    - Scan `example/adaptors` to list available adaptors in the hierarchical format.
    - Use **Apache Commons Compress** to extract specific `.iar` files to the user's temp directory.
    - Invoke `LockingService` before extraction.

### 6. Metadata Service
- **Goal:** Parse and model the metadata.
- **Logic:**
    - Parse `*_ref.txt` files (CSV-like) using **Apache Commons CSV**.
    - Map parsed data to internal domain POJOs (e.g., `CounterDef`, `MocDef`).
    - Establish relationships (e.g., link `MocDef` to its parent via ID).

### 7. Transformation Service
- **Goal:** Decouple backend models from frontend API contracts.
- **Logic:**
    - Use **MapStruct** to define mappings between Domain POJOs and API DTOs.
    - Support bi-directional mapping (Entity -> DTO, DTO -> Entity).

## Technical Constraints
- **Testing:** Minimum 80% code coverage using JUnit 5, Mockito, and Spring Boot Test.
- **Style:** Follow the defined Java and Spring style guides.
- **Performance:** Stream large files where possible; use buffered I/O.
