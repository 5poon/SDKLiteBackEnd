# Implementation Plan - Track: Adaptor Versioning & Publication

## Phase 1: Versioning & Repackaging Logic
- [ ] Task: Implement Versioning Service
    - [ ] Create `VersioningService`.
    - [ ] Implement `suggestNextVersion(vendor, tech, name)` logic.
    - [ ] Write Unit Tests for version incrementing.
- [ ] Task: Implement IAR Packager
    - [ ] Create `IarPackagerService`.
    - [ ] Implement `packageConfig(sourceDir, targetIarPath)` using Apache Commons Compress.
    - [ ] Write Unit/Integration Tests creating a ZIP from a dummy directory.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Versioning & Repackaging Logic' (Protocol in workflow.md)

## Phase 2: Publication API
- [ ] Task: Create Publish API
    - [ ] Create `PublishRequest` DTO.
    - [ ] Add `POST /api/v1/metadata/publish` to `MetadataController`.
    - [ ] Implement orchestration in `MetadataServiceImpl`:
        - [ ] Verify lock.
        - [ ] Package `config`.
        - [ ] Create target version directory.
        - [ ] Move IAR.
        - [ ] Close Session.
- [ ] Task: Final Verification
    - [ ] Write Integration Tests verifying that a new folder and IAR appear in `example/adaptors` after publishing.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Publication API' (Protocol in workflow.md)
