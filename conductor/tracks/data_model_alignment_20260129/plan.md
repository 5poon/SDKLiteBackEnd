# Implementation Plan - Track: Data Model & CSV Alignment Audit

## Phase 1: Entity POJO Audit & Update
- [ ] Task: Update Import Entity Models
    - [ ] Update `NeImportEntity`, `CounterImportEntity`, `AttrImportEntity` POJOs with all identified CSV fields.
- [ ] Task: Update MOC Models
    - [ ] Update `MocDef` and `VendorMocDef` with technical fields (flags, behaviour, etc.).
- [ ] Task: Update Counter & Attribute Models
    - [ ] Update `CounterDef` and `MocAttributeDef` with explicit technical fields.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Entity POJO Audit & Update' (Protocol in workflow.md)

## Phase 2: New Supporting Models
- [ ] Task: Implement Counter Granularity Model
    - [ ] Create `CounterDefGran` POJO and DTO.
- [ ] Task: Implement Mapping Models
    - [ ] Create `ImportCounterFor` and `ImportAttrFor` POJOs.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: New Supporting Models' (Protocol in workflow.md)

## Phase 3: DTO & Mapper Synchronization
- [ ] Task: Update API DTOs
    - [ ] Expand all DTOs to include the new fields.
- [ ] Task: Update MetadataMapper
    - [ ] Refine MapStruct mappings to handle explicit fields.
- [ ] Task: Update Parser & Serializer
    - [ ] Ensure `MetadataParserServiceImpl` and `MetadataSerializerServiceImpl` use the new explicit fields.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: DTO & Mapper Synchronization' (Protocol in workflow.md)
