# Implementation Plan - Track: Advanced Metadata Graph Linking

## Phase 1: Advanced Linking Logic
- [ ] Task: Update MetadataGraphService
    - [ ] Add `List<CounterDefGran> granularities` parameter to `buildGraph`.
    - [ ] Implement logic to link Counters to Entities via the Granularity bridge.
    - [ ] Update Attribute linking logic to strictly follow `idf_mapped_attribute`.
- [ ] Task: Update MetadataServiceImpl
    - [ ] Refactor `getDataSourceHierarchy` to load granularities and pass to the graph service.
- [ ] Task: Final Validation
    - [ ] Verify that the bootstrap response shows Counters and Attributes nested correctly within their respective Entities under the Data Source.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Advanced Linking Logic' (Protocol in workflow.md)
