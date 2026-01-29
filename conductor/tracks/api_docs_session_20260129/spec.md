# Track Specification: API Documentation & Session Management

## Objective
Enhance the developer experience and system maintenance by providing interactive API documentation and implementing a formal session lifecycle. This ensures that resources (locks and temporary files) are correctly managed and released.

## Core Requirements

### 1. Interactive API Documentation (OpenAPI/Swagger)
- Integrate `springdoc-openapi-starter-webmvc-ui`.
- Configure the Swagger UI to be available at `/swagger-ui.html`.
- Add descriptive OpenAPI annotations to `MetadataController` and other endpoints to clarify parameter usage (e.g., `timestamp`, `adaptorName`).

### 2. Session Closure API
- **Endpoint:** `POST /api/v1/session/close`
- **Parameters:** `timestamp`, `adaptorName`.
- **Logic:**
    - Explicitly release the lock held by the user for the specified adaptor using `LockService`.
    - (Optional but Recommended) Delete the associated directory in `example/user_temp/<username>/<timestamp>`.

### 3. Automated Cleanup Service
- **Logic:**
    - Implement a scheduled background task using Spring's `@Scheduled`.
    - Scan the `example/user_temp` root directory.
    - Delete any directory older than a configurable TTL (e.g., 24 hours).
    - Ensure that locks associated with deleted directories are also purged from `LockService` if applicable.

## Technical Constraints
- **Security:** Swagger UI should be accessible but may require basic auth based on security policy.
- **Data Integrity:** The cleanup task must not delete active sessions.
