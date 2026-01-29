# Implementation Plan - Track: Unified Project Context API

## Phase 1: DTO Consolidation
- [x] Task: Create Unified DTOs
    - [x] Create `ProjectContextDTO`.
    - [x] Ensure all sub-DTOs (`CounterImportEntityDTO`, etc.) are defined.
    - [x] Update `MetadataMapper` to handle the top-level consolidation.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: DTO Consolidation' (Protocol in workflow.md)

## Phase 2: Full Context Loading logic
- [x] Task: Implement Unified Loading [commit: 07aae57]
    - [x] Update `MetadataService` with `getProjectContext(username, timestamp, adaptorName)` method.
    - [x] Implement orchestration logic to load all 10+ CSV files.
    - [x] Assemble the full hierarchy and tree.
    - [x] Write Unit Tests for full context assembly.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Full Context Loading logic' (Protocol in workflow.md)

## Phase 3: Bootstrap API
- [ ] Task: Implement Context Endpoint
    - [ ] Add `GET /api/v1/metadata/context` to `MetadataController`.
    - [ ] Write Integration Tests verifying the "Mega JSON" structure.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Bootstrap API' (Protocol in workflow.md)
