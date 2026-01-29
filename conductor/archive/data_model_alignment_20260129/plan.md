# Implementation Plan - Track: Data Model & CSV Alignment Audit

## Phase 1: Entity POJO Audit & Update
- [x] Task: Update Import Entity Models [commit: 358e4cf]
    - [ ] Update `NeImportEntity`, `CounterImportEntity`, `AttrImportEntity` POJOs with all identified CSV fields.
- [x] Task: Update MOC Models [commit: b3b56e7]
    - [ ] Update `MocDef` and `VendorMocDef` with technical fields (flags, behaviour, etc.).
- [x] Task: Update Counter & Attribute Models [commit: 57e7c6b]
    - [ ] Update `CounterDef` and `MocAttributeDef` with explicit technical fields.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Entity POJO Audit & Update' (Protocol in workflow.md)

## Phase 2: New Supporting Models [checkpoint: 7742818]
- [x] Task: Implement Counter Granularity Model
- [x] Task: Implement Mapping Models
- [ ] Task: Conductor - User Manual Verification 'Phase 2: New Supporting Models' (Protocol in workflow.md)

## Phase 3: DTO & Mapper Synchronization [checkpoint: 25b52ba]
- [x] Task: Update API DTOs
- [x] Task: Update MetadataMapper
- [x] Task: Update Parser & Serializer
