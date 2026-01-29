# Product Definition: SDKLiteBackEnd

## Product Vision
SDKLiteBackEnd is a Maven-based Spring Boot application designed to serve as a robust backend service for a frontend GUI. Its primary purpose is to act as a data proxy, efficiently bridging the gap between existing file-based data structures and the user interface.

## Target Users
- **End-users:** Individuals interacting with the frontend GUI who require seamless access to data and configurations managed by the backend.

## Core Features
- **File-Based User Authentication & Quotas:**
    - A dedicated REST/Web controller to process username and password credentials.
    - User profiles will be stored in a local text or JSON file.
    - This file will explicitly define user roles and functional limits/quotas, such as the maximum number of data sources, counters, and attributes a specific user is permitted to create.
- **Hierarchical Adaptor Management:**
    - Authenticated users can browse and load existing adaptor configurations from the `example/adaptors` directory.
    - Adaptors follow a vendor/technology/adaptor_name/version hierarchy (e.g., `nkia/gnodeb/nkia_gnodeb_csv_nr25r2/03.01.01`).
    - The backend provides a complete list of available adaptors in JSON format to the frontend.
- **Concurrency Control (Locking):**
    - Implement a locking mechanism to ensure exclusive access to an adaptor version.
    - If User A opens an adaptor (e.g., `03.01.01`), it is locked.
    - If User B attempts to open the same adaptor, the request is denied, and the system returns information identifying User A as the current lock holder.
    - The lock is released only when the holding user explicitly closes the session or successfully publishes a new version.
- **Adaptor Configuration Extraction:**
    - Upon user selection of an adaptor and version, the frontend sends this information to a specific REST/Web controller.
    - The backend extracts the configuration from the selected `.iar` (ZIP) archive into a user-specific temporary directory structure: `example/user_temp/<username>/<timestamp>/config/`.
    - This structure includes `interfaces` and `metadata` subdirectories containing the extracted adaptor files.
- **Metadata Object Mapping & Relationship Analysis:**
    - Implement DTOs (Data Transfer Objects) and POJOs (Plain Old Java Objects) to structurally map and load the contents of the extracted metadata files (e.g., `*_ref.txt`).
    - The system must parse these CSV-like text files (e.g., `counter_def_ref.txt`, `moc_def_parent_ref.txt`) to establish relationships between entities (e.g., Parent-Child MOC relationships, Counter definitions).
    - This involves analyzing the `interfaces` and `metadata` folders, including the `nml` (Network Model Layer) subdirectory, to ensure accurate data modeling.
- **Bi-Directional Transformation Service:**
    - Implement a dedicated service to perform two-way transformation:
        1.  **Backend to Frontend:** Convert internal backend metadata POJOs into frontend-optimized DTOs for GUI rendering.
        2.  **Frontend to Backend:** Convert incoming JSON data from the GUI back into backend metadata POJOs for processing and storage.
    - This ensures seamless synchronization between the backend data model and the frontend user interface.
- **Real-Time CRUD via REST:**
    - Expose REST/Web controllers to handle CRUD operations directly targeting the extracted data.
    - When a CRUD request is received and processed, the backend will update the relevant files directly within the user-specific directory: `example/user_temp/<username>/<timestamp>/config/`.
- **Unified Project Context API:**
    - Provide a high-performance "Bootstrap" API endpoint that returns the complete metadata state (DataSources, Entities, MOCs, Counters, and Attributes) in a single unified JSON payload, optimizing frontend initialization.
- **Session Management & Automated Cleanup:**
    - **Explicit Session Closure:** Provide an API endpoint to explicitly release adaptor locks and delete temporary workspaces when a user finishes their session.
    - **Background Cleanup:** Implement a scheduled service to automatically purge stale temporary directories based on a configurable TTL (Time-To-Live), ensuring efficient disk space usage.
- **Interactive API Documentation:**
    - Integrated Swagger UI (OpenAPI) to provide developers with a real-time, interactive interface for exploring and testing all backend service endpoints.
- **Publish & Repackaging:**
    - **Save:** Intermediate saves update the files in the temporary directory.
    - **Publish:** Upon a user request to "Publish," the backend will take the current state of the `config` directory from `example/user_temp/<username>/<timestamp>/`.
    - It will compress this directory into a new `.iar` archive.
    - This new archive will be assigned a new version number, finalizing the user's changes as a distributable package.
- **Data Proxying:** Serve as the central hub for reading and writing data from the existing file structures (e.g., `.iar`, `.csv`, `.txt`, `.properties`) found in the project's example directories.
- **JSON Communication:** Expose RESTful API endpoints that consume and produce JSON content for all data operations.
- **Spring Security Integration:** Implement robust authentication and authorization mechanisms to ensure secure access to the data proxy services.

## Technical Goals
- **Spring Boot Ecosystem:** Utilize the Spring Boot framework to ensure a scalable and maintainable backend architecture.
- **Maven Management:** Employ Maven for dependency management and build automation.
- **Security First:** Integrate Spring Security from the outset to protect sensitive data and service endpoints.
