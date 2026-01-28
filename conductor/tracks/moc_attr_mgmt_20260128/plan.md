# Implementation Plan - Track: MOC Hierarchy & Attribute Management

## Phase 1: MOC Tree Building
- [ ] Task: Implement MOC Hierarchy Logic
    - [ ] Create `MocHierarchyService`.
    - [ ] Implement `buildTree(List<MocDef> mocs, List<MocDefParent> parents)` logic.
    - [ ] Link `VendorMocDef` to `MocDef`.
- [ ] Task: Refactor Metadata Service
    - [ ] Update `MetadataService.getDataSourceHierarchy` to be dynamic (remove hardcoded adaptor name).
    - [ ] Implement `getMocHierarchy(username, timestamp, adaptorName)`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: MOC Tree Building' (Protocol in workflow.md)

## Phase 2: Attribute Support & CRUD
- [ ] Task: Implement Attribute Models & Parsing
    - [ ] Create `MocAttributeDef` and `ImportAttrFor` POJOs.
    - [ ] Update `MetadataParserService` and `MetadataSerializerService`.
- [ ] Task: Implement Attribute CRUD
    - [ ] Update `MetadataCrudService` with `create/update/deleteAttribute` methods.
    - [ ] Implement quota check for `maxAttributes`.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Attribute Support & CRUD' (Protocol in workflow.md)

## Phase 3: Controller Expansion
- [ ] Task: Implement MOC & Attribute Endpoints
    - [ ] Add `GET /api/v1/metadata/mocs/tree` to `MetadataController`.
    - [ ] Add Attribute CRUD endpoints.
    - [ ] Write Integration Tests.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Controller Expansion' (Protocol in workflow.md)
