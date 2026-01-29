# Implementation Plan - Track: Adaptor Versioning & Publication

## Phase 1: Versioning & Repackaging Logic [checkpoint: adf450f]
- [x] Task: Implement Versioning Service
- [x] Task: Implement IAR Packager
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Versioning & Repackaging Logic' (Protocol in workflow.md)

## Phase 2: Publication API
- [x] Task: Create Publish API [commit: f6acb76]
    - [ ] Create `PublishRequest` DTO.
    - [ ] Add `POST /api/v1/metadata/publish` to `MetadataController`.
    - [ ] Implement orchestration in `MetadataServiceImpl`:
        - [ ] Verify lock.
        - [ ] Package `config`.
        - [ ] Create target version directory.
        - [ ] Move IAR.
        - [ ] Close Session.
- [~] Task: Final Verification
    - [ ] Write Integration Tests verifying that a new folder and IAR appear in `example/adaptors` after publishing.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Publication API' (Protocol in workflow.md)
