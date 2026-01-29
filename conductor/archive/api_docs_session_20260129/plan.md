# Implementation Plan - Track: API Documentation & Session Management

## Phase 1: Interactive API Documentation
- [x] Task: Configure Springdoc OpenAPI [commit: 931bd9c]
    - [x] Add `springdoc-openapi-starter-webmvc-ui` dependency to `pom.xml`.
    - [x] Configure basic OpenAPI info (Title, Version) in a `@Configuration` class.
    - [x] Add `@Operation` and `@Parameter` annotations to `MetadataController`.
    - [x] Verify Swagger UI accessibility at `/swagger-ui.html`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Interactive API Documentation' (Protocol in workflow.md)

## Phase 2: Session Closure API [checkpoint: 04b0d3b]
- [x] Task: Implement Session Service
- [x] Task: Create Session Controller
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Session Closure API' (Protocol in workflow.md)

## Phase 3: Automated Cleanup [checkpoint: 6148060]
- [x] Task: Implement Background Cleanup Task
    - [ ] Create `CleanupScheduler`.
    - [ ] Implement `@Scheduled` method to purge stale directories in `user_temp`.
    - [ ] Configure TTL via `application.properties`.
    - [ ] Write Unit/Integration Tests for cleanup logic using mock time or small TTL.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Automated Cleanup' (Protocol in workflow.md)
