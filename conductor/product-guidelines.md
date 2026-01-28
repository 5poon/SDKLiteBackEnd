# Product Guidelines

## Documentation Tone & Style
- **Technical & Precise:** API documentation (OpenAPI/Swagger) and error messages must be concise, accurate, and targeted towards developers.
- **Explicit Errors:** Error responses should provide specific technical details (e.g., error codes, field names) to aid debugging.

## Performance & Scalability
- **Memory Efficiency:**
  - Implement streaming or chunked processing for large metadata files (like `counter_def_ref.txt`) to minimize memory footprint.
  - Avoid loading entire large files into memory unless necessary for specific transformation tasks.
- **High Throughput:**
  - Optimize I/O operations for concurrent access.
  - Use non-blocking I/O where applicable, or a well-tuned thread pool for file operations.

## Data Integrity & Reliability
- **Atomic Writes:** All file updates within the `user_temp` directory must be atomic. Write to a temporary file first, then perform an atomic rename to replace the target file. This prevents data corruption in case of system failure.
- **Path Validation:** Strictly sanitize and validate all file paths received from the frontend to prevent Path Traversal attacks. Ensure all file operations are confined within the `example/` directory structure.

## Security Principles
- **Credential Storage:** User passwords must never be stored in plain text. Use strong hashing algorithms (e.g., BCrypt) for the file-based user store.
- **Role-Based Access Control (RBAC):** strictly enforce the user quotas (data sources, counters, attributes) defined in the user profile file at the service layer.
- **Secure File Access:** Ensure that authenticated users can only access their own temporary directories and the public `adaptors` directory.

## Concurrency Strategy
- **Session Isolation:** Since each user operates in a unique `user_temp/<timestamp>` directory, cross-user conflict is naturally minimized.
- **Request Handling:** While the frontend uses distinct controllers, the backend should handle requests safely, ensuring that rapid-fire requests from the same session do not corrupt the file state (e.g., via synchronized blocks or file locks if necessary).
