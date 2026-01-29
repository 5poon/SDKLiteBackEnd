# Implementation Plan - Track: Refine Data Structure & DTO Nesting

## Phase 1: DTO & Mapper Refinement
- [x] Task: Update DTOs [commit: 274368b]
    - [ ] Modify `ImportDataSourceDTO` to remove `neEntities`.
    - [ ] Verify `CounterImportEntityDTO` and `AttrImportEntityDTO` nesting.
- [x] Task: Update MetadataMapper [commit: 59c35a9]
    - [ ] Update mapping logic to match the new DTO structure.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: DTO & Mapper Refinement' (Protocol in workflow.md)

## Phase 2: Service Logic Update
- [x] Task: Update MetadataServiceImpl [commit: ce1600d]
    - [ ] Ensure `getProjectContext` correctly populates the nested hierarchy.
- [ ] Task: Final Verification
    - [ ] Verify the `/api/v1/metadata/context` response structure matches requirements.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Service Logic Update' (Protocol in workflow.md)
