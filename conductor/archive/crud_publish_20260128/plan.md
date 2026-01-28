# Implementation Plan - Track: CRUD Operations & Publish Lifecycle

## Phase 1: Serialization & User Work Area Persistence
- [x] Task: Implement CSV Serialization [commit: 0c11e46]
    - [x] Add CSV serialization logic to `MetadataParserService` (or create `MetadataSerializerService`).
    - [x] Implement `toCsv(List<T> entities)` for each metadata type.
    - [x] Write Unit Tests verifying the CSV output matches the original file format.
- [x] Task: Implement Work Area Persistence [commit: a45a2bd]
    - [x] Create `WorkAreaService` to manage the lifecycle of the user's `user_temp` folder.
    - [x] Implement `save(username, timestamp, metadataType, entities)` method using `FileService.atomicWrite`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Serialization & User Work Area Persistence' (Protocol in workflow.md)

## Phase 2: CRUD REST API & Quota Enforcement [checkpoint: 5abb29a]
- [x] Task: Implement CRUD Services
- [x] Task: Implement Quota Enforcement
- [x] Task: Create CRUD Controllers

## Phase 3: Publish & Versioning
- [ ] Task: Implement Repackaging Logic
    - [ ] Create `PublishService`.
    - [ ] Implement ZIP compression of the `config` directory using Apache Commons Compress.
- [ ] Task: Implement Version Management & Finalization
    - [ ] Implement logic to move the new IAR to the target `adaptors` directory.
    - [ ] Implement `LockService.releaseLock` upon success.
    - [ ] Add `POST /api/v1/metadata/publish` endpoint.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Publish & Versioning' (Protocol in workflow.md)
