# Implementation Plan - Track: Refine Data Structure & DTO Nesting

## Phase 1: DTO & Mapper Refinement
- [ ] Task: Update DTOs
    - [ ] Modify `ImportDataSourceDTO` to remove `neEntities`.
    - [ ] Verify `CounterImportEntityDTO` and `AttrImportEntityDTO` nesting.
- [ ] Task: Update MetadataMapper
    - [ ] Update mapping logic to match the new DTO structure.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: DTO & Mapper Refinement' (Protocol in workflow.md)

## Phase 2: Service Logic Update
- [ ] Task: Update MetadataServiceImpl
    - [ ] Ensure `getProjectContext` correctly populates the nested hierarchy.
- [ ] Task: Final Verification
    - [ ] Verify the `/api/v1/metadata/context` response structure matches requirements.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Service Logic Update' (Protocol in workflow.md)
