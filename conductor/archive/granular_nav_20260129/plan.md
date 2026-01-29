# Implementation Plan - Track: Granular Metadata Navigation API

## Phase 1: Granular Service Logic
- [x] Task: Expand MetadataServiceImpl for Filtering [commit: 0c2ab0c]
    - [ ] Implement `getCountersByEntityId(username, timestamp, adaptorName, entityId)`.
    - [ ] Implement `getAttributesByEntityId(username, timestamp, adaptorName, entityId)`.
    - [ ] Implement `getCounterById` and `getAttributeById`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Granular Service Logic' (Protocol in workflow.md)

## Phase 2: Targeted REST Endpoints
- [x] Task: Update MetadataController [commit: e18f956]
    - [ ] Add `GET /api/v1/metadata/datasources/{id}/entities`.
    - [ ] Add `GET /api/v1/metadata/entities/{id}/counters`.
    - [ ] Add `GET /api/v1/metadata/entities/{id}/attributes`.
    - [ ] Add `GET /api/v1/metadata/counters/{id}` and `GET /api/v1/metadata/attributes/{id}`.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Targeted REST Endpoints' (Protocol in workflow.md)

## Phase 3: Verification
- [~] Task: Integration Audit
    - [ ] Write integration tests for each new granular path.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Verification' (Protocol in workflow.md)
