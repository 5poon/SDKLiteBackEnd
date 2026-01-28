# Tech Stack: SDKLiteBackEnd

## Core Framework
- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Build System:** Maven

## Core Dependencies
- **Security:** Spring Security (Authentication, Authorization, RBAC)
- **JSON Processing:** Jackson (Built-in Spring Boot support)
- **Archive Handling:** Apache Commons Compress (For `.iar` / ZIP extraction and repackaging)
- **Boilerplate Reduction:** Project Lombok
- **Object Mapping:** MapStruct (For Backend POJO <-> Frontend DTO transformations)

## Data Processing
- **CSV/Text Parsing:** Apache Commons CSV or OpenCSV (For robust parsing of `*_ref.txt` files)
- **File I/O:** Java NIO (Non-blocking I/O for efficient file operations)

## Testing Stack
- **Unit Testing:** JUnit 5
- **Mocking:** Mockito
- **Integration Testing:** Spring Boot Test
- **API Testing:** RestAssured

## Development Tools
- **API Documentation:** Springdoc OpenAPI (Swagger UI)
