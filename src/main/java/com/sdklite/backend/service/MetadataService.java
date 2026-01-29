package com.sdklite.backend.service;

import com.sdklite.backend.dto.ProjectContextDTO;
import com.sdklite.backend.model.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MetadataService {
    List<ImportDataSource> getDataSourceHierarchy(String username, String timestamp, String adaptorName, int depth);
    Optional<ImportDataSource> getDataSourceById(String username, String timestamp, String adaptorName, String id, int depth);
    List<MocDef> getMocHierarchy(String username, String timestamp, String adaptorName, int depth);
    ProjectContextDTO getProjectContext(String username, String timestamp, String adaptorName);

    // Granular Navigation
    List<CounterDef> getCountersByEntityId(String username, String timestamp, String adaptorName, String entityId);
    List<MocAttributeDef> getAttributesByEntityId(String username, String timestamp, String adaptorName, String entityId);
    Optional<CounterDef> getCounterById(String username, String timestamp, String adaptorName, String id);
    Optional<MocAttributeDef> getAttributeById(String username, String timestamp, String adaptorName, String id);

    void publishAdaptor(String username, String timestamp, String adaptorName, String vendor, String technology, String newVersion) throws IOException;
    String suggestNextVersion(String vendor, String technology, String adaptorName);
}
