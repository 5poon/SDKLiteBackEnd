# Implementation Plan - Track: CRUD Operations & Publish Lifecycle

## Phase 1: Serialization & User Work Area Persistence
- [ ] Task: Implement CSV Serialization
    - [ ] Add CSV serialization logic to `MetadataParserService` (or create `MetadataSerializerService`).
    - [ ] Implement `toCsv(List<T> entities)` for each metadata type.
    - [ ] Write Unit Tests verifying the CSV output matches the original file format.
- [ ] Task: Implement Work Area Persistence
    - [ ] Create `WorkAreaService` to manage the lifecycle of the user's `user_temp` folder.
    - [ ] Implement `save(username, timestamp, metadataType, entities)` method using `FileService.atomicWrite`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Serialization & User Work Area Persistence' (Protocol in workflow.md)

## Phase 2: CRUD REST API & Quota Enforcement
- [ ] Task: Implement CRUD Services
    - [ ] Create `MetadataCrudService`.
    - [ ] Implement logic to Load -> Modify -> Save for specific metadata types.
- [ ] Task: Implement Quota Enforcement
    - [ ] Add check in `MetadataCrudService` to retrieve current user quotas from the security context.
    - [ ] Prevent "Create" operations if quota is exceeded.
- [ ] Task: Create CRUD Controllers
    - [ ] Implement `POST`, `PUT`, `DELETE` endpoints in `MetadataController`.
    - [ ] Write Integration Tests verifying that file content changes after API calls.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: CRUD REST API & Quota Enforcement' (Protocol in workflow.md)

## Phase 3: Publish & Versioning
- [ ] Task: Implement Repackaging Logic
    - [ ] Create `PublishService`.
    - [ ] Implement ZIP compression of the `config` directory using Apache Commons Compress.
- [ ] Task: Implement Version Management & Finalization
    - [ ] Implement logic to move the new IAR to the target `adaptors` directory.
    - [ ] Implement `LockService.releaseLock` upon success.
    - [ ] Add `POST /api/v1/metadata/publish` endpoint.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Publish & Versioning' (Protocol in workflow.md)
