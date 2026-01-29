# Implementation Plan - Track: Final Integration & Production Hardening

## Phase 1: Enriched Data Hierarchy
- [ ] Task: Update DTO Nesting
    - [ ] Add `List<CounterDefDTO> counters` to `CounterImportEntityDTO`.
    - [ ] Add `List<MocAttributeDefDTO> attributes` to `AttrImportEntityDTO`.
    - [ ] Add `counters` and `attributes` lists to `MocDefDTO`.
- [ ] Task: Implement Hierarchy Linker
    - [ ] Update `MetadataGraphService` to link Counters/Attributes to their respective Entities.
    - [ ] Update `MocHierarchyService` to bind Counters/Attributes to logical MOCs using mapping files (`import_*_for_ref.txt`).
- [ ] Task: Update Context Loading
    - [ ] Update `MetadataServiceImpl.getProjectContext` to return the fully nested structure.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Enriched Data Hierarchy' (Protocol in workflow.md)

## Phase 2: Global Error Handling & Validation
- [ ] Task: Implement Global Error Handler
    - [ ] Create `GlobalExceptionHandler` with `@RestControllerAdvice`.
    - [ ] Define `ErrorResponse` DTO.
    - [ ] Implement handlers for core application exceptions.
- [ ] Task: Implement Request Validation
    - [ ] Add `spring-boot-starter-validation` dependency.
    - [ ] Add `@NotBlank` and `@NotNull` to CRUD DTOs.
    - [ ] Add `@Valid` to controller methods.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Global Error Handling & Validation' (Protocol in workflow.md)

## Phase 3: Final Verification & Code Quality
- [ ] Task: Final Integration Audit
    - [ ] Perform a full bootstrap call (`/context`) and verify JSON nesting.
    - [ ] Verify Swagger documentation reflects all new nested fields.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Final Verification & Code Quality' (Protocol in workflow.md)
