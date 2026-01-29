# Track Specification: Adaptor Versioning & Publication

## Objective
Enable users to finalize their changes by creating a new version of an adaptor. This track implements the logic to repackage modified metadata into a standard `.iar` archive and store it in the appropriate hierarchical directory.

## Core Requirements

### 1. Version Increment Logic
- Implement a service to identify the current highest version for an adaptor (e.g., `03.01.01`).
- Provide an algorithm to suggest the next version by incrementing the last numeric component (e.g., `03.01.02`).
- Allow the user to override this with a custom version string.

### 2. IAR Repackaging (ZIP)
- Use **Apache Commons Compress** to recursively ZIP the `config/` directory from the user's temporary work area.
- The resulting `.iar` must have the same internal structure as the original (e.g., the `config` folder should be at the root of the ZIP or as per the original IAR's internal structure).

### 3. Publication Workflow
- Create the target directory: `example/adaptors/<vendor>/<tech>/<name>/<new_version>/`.
- Move the generated `.iar` file to this directory.
- **Cleanup:** Invoke `SessionService.closeSession` to release locks and delete the `user_temp` folder.

### 4. Publication API
- **Endpoint:** `POST /api/v1/metadata/publish`
- **Body:**
    ```json
    {
      "timestamp": "...",
      "adaptorName": "...",
      "vendor": "...",
      "technology": "...",
      "newVersion": "03.01.02"
    }
    ```

## Technical Constraints
- **Atomicity:** The publish operation should be as atomic as possible. If ZIP creation fails, the target directory should not be created.
- **Validation:** Ensure the `newVersion` does not already exist.
