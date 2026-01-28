# Implementation Plan - Track: Initial Core Services & Foundation

## Phase 1: Project Skeleton & Security Infrastructure [checkpoint: d8c6db9]
- [x] Task: Initialize Spring Boot Project [commit: e959603]
    - [x] Create `pom.xml` with dependencies (Spring Web, Security, Lombok, Commons Compress, Commons CSV, MapStruct).
    - [x] Create main application class `SDKLiteBackEndApplication`.
    - [x] Configure `application.properties` (logging, server port).
- [x] Task: Implement File-Based Authentication [commit: 5ca6c77]
    - [x] Define `User` domain model (username, password hash, roles, quotas).
    - [x] Create `CustomUserDetailsService` to load users from a local JSON file.
    - [x] Configure `SecurityConfig` (SecurityFilterChain) to use the custom service and enable HTTP Basic or Form Login.
    - [x] Write Unit Tests for `CustomUserDetailsService` (Mock file reading).
    - [x] Write Integration Test for Login Endpoint.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Project Skeleton & Security Infrastructure' (Protocol in workflow.md)

## Phase 2: Core Utility Services (File & Locking) [checkpoint: 3d0c018]
- [x] Task: Implement File Management Service [commit: 4995c52]
    - [x] Create `FileService` interface and implementation.
    - [x] Implement `resolveUserTempPath(user, timestamp)` method.
    - [x] Implement `atomicWrite(path, content)` method.
    - [x] Implement `validatePath(path)` for security.
    - [x] Write Unit Tests for path validation and atomic writes.
- [x] Task: Implement Locking Service [commit: 2678b0f]
    - [x] Define `Lock` domain model.
    - [x] Create `LockService` to manage in-memory locks (ConcurrentHashMap).
    - [x] Implement `acquireLock(adaptorId, userId)` and `releaseLock(adaptorId, userId)`.
    - [x] Write Unit Tests for concurrency scenarios (multiple users trying to acquire same lock).
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Core Utility Services (File & Locking)' (Protocol in workflow.md)

## Phase 3: Adaptor Management & Extraction
- [x] Task: Implement Adaptor Listing [commit: 6c244a6]
    - [x] Create `AdaptorDiscoveryService` to scan `example/adaptors`.
    - [x] Define `AdaptorInfo` DTO.
    - [x] Implement hierarchical directory traversal.
    - [x] Write Unit Tests with mock file system structure.
- [ ] Task: Implement Adaptor Extraction
    - [ ] Create `AdaptorExtractionService`.
    - [ ] Implement `extractIar(path, destination)` using Apache Commons Compress.
    - [ ] Integrate `LockService`: Ensure lock is acquired before extraction.
    - [ ] Integrate `FileService`: Use secure path resolution.
    - [ ] Write Integration Tests extracting a sample dummy `.iar` file.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Adaptor Management & Extraction' (Protocol in workflow.md)

## Phase 4: Metadata Parsing & Transformation
- [ ] Task: Implement Metadata Parsing (Commons CSV)
    - [ ] Define Domain POJOs for `CounterDef`, `MocDef`, etc.
    - [ ] Create `MetadataParserService`.
    - [ ] Implement generic parser for `*_ref.txt` files (handling headers and delimiters).
    - [ ] Write Unit Tests for parsing sample CSV content.
- [ ] Task: Implement Transformation Service (MapStruct)
    - [ ] Define API DTOs (`CounterDefDTO`, `MocDefDTO`).
    - [ ] Create MapStruct mappers (`MetadataMapper`).
    - [ ] Implement `toDTO` and `toEntity` methods.
    - [ ] Write Unit Tests to verify field mapping accuracy.
- [ ] Task: Conductor - User Manual Verification 'Phase 4: Metadata Parsing & Transformation' (Protocol in workflow.md)
