# Implementation Plan - Track: MOC Hierarchy & Attribute Management

## Phase 1: MOC Tree Building
- [x] Task: Implement MOC Hierarchy Logic [commit: ede0f16]
    - [x] Create `MocHierarchyService`.
    - [x] Implement `buildTree(List<MocDef> mocs, List<MocDefParent> parents)` logic.
    - [x] Link `VendorMocDef` to `MocDef`.
- [x] Task: Refactor Metadata Service [commit: ede0f16]
    - [x] Update `MetadataService.getDataSourceHierarchy` to be dynamic (remove hardcoded adaptor name).
    - [x] Implement `getMocHierarchy(username, timestamp, adaptorName)`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: MOC Tree Building' (Protocol in workflow.md)

## Phase 2: Attribute Support & CRUD [checkpoint: 7b7dafd]
- [x] Task: Implement Attribute Models & Parsing
- [x] Task: Implement Attribute CRUD
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Attribute Support & CRUD' (Protocol in workflow.md)

## Phase 3: Controller Expansion
- [ ] Task: Implement MOC & Attribute Endpoints
    - [ ] Add `GET /api/v1/metadata/mocs/tree` to `MetadataController`.
    - [ ] Add Attribute CRUD endpoints.
    - [ ] Write Integration Tests.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Controller Expansion' (Protocol in workflow.md)
