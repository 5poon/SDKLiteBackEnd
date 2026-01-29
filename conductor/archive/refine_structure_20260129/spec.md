# Track Specification: Refine Data Structure & DTO Nesting

## Objective
Refine the API response structure to align with the GUI's requirements. This involves removing NE Entities from the Data Source hierarchy and ensuring that Counters and Attributes are strictly nested within their parent Entities (CounterImportEntity and AttrImportEntity).

## Core Requirements

### 1. DTO Structural Changes
- **ImportDataSourceDTO:** Remove `List<NeImportEntityDTO> neEntities`.
- **ProjectContextDTO:** Ensure it contains the flat list of `neEntities` at the top level, while `counterEntities` and `attrEntities` remain nested under `dataSources` (and contain their own nested data).

### 2. Nesting Verification
- Ensure `CounterImportEntityDTO` contains `List<CounterDefDTO> counters`.
- Ensure `AttrImportEntityDTO` contains `List<MocAttributeDefDTO> attributes`.

### 3. Logic & Mapping Alignment
- Update `MetadataMapper` to handle the removal of `neEntities` from the Data Source mapping.
- Update `MetadataServiceImpl` to ensure that when `ProjectContextDTO` is built, the nested lists in the entities are fully populated.

## Technical Constraints
- **Mapping:** Use MapStruct `@Mapping` annotations to handle nested collection mapping correctly.
