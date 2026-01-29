# Implementation Plan - Track: API Documentation & Session Management

## Phase 1: Interactive API Documentation
- [ ] Task: Configure Springdoc OpenAPI
    - [ ] Add `springdoc-openapi-starter-webmvc-ui` dependency to `pom.xml`.
    - [ ] Configure basic OpenAPI info (Title, Version) in a `@Configuration` class.
    - [ ] Add `@Operation` and `@Parameter` annotations to `MetadataController`.
    - [ ] Verify Swagger UI accessibility at `/swagger-ui.html`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Interactive API Documentation' (Protocol in workflow.md)

## Phase 2: Session Closure API
- [ ] Task: Implement Session Service
    - [ ] Create `SessionService` to encapsulate closure logic.
    - [ ] Implement `closeSession(username, timestamp, adaptorName)` method.
- [ ] Task: Create Session Controller
    - [ ] Create `SessionController` with `POST /api/v1/session/close` endpoint.
    - [ ] Write Integration Tests verifying lock release and directory deletion.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Session Closure API' (Protocol in workflow.md)

## Phase 3: Automated Cleanup
- [ ] Task: Implement Background Cleanup Task
    - [ ] Create `CleanupScheduler`.
    - [ ] Implement `@Scheduled` method to purge stale directories in `user_temp`.
    - [ ] Configure TTL via `application.properties`.
    - [ ] Write Unit/Integration Tests for cleanup logic using mock time or small TTL.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Automated Cleanup' (Protocol in workflow.md)
