package com.sdklite.backend.service;

import com.sdklite.backend.dto.ProjectContextDTO;
import com.sdklite.backend.model.ImportDataSource;
import com.sdklite.backend.model.MocDef;
import java.util.List;
import java.util.Optional;

public interface MetadataService {
    List<ImportDataSource> getDataSourceHierarchy(String username, String timestamp, String adaptorName, int depth);
    Optional<ImportDataSource> getDataSourceById(String username, String timestamp, String adaptorName, String id, int depth);
    List<MocDef> getMocHierarchy(String username, String timestamp, String adaptorName, int depth);
    ProjectContextDTO getProjectContext(String username, String timestamp, String adaptorName);
}