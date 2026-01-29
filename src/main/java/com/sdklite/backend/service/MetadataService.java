package com.sdklite.backend.service;

import com.sdklite.backend.dto.ProjectContextDTO;
import com.sdklite.backend.model.ImportDataSource;
import com.sdklite.backend.model.MocDef;
import java.util.List;

public interface MetadataService {
    List<ImportDataSource> getDataSourceHierarchy(String username, String timestamp, String adaptorName);
    List<MocDef> getMocHierarchy(String username, String timestamp, String adaptorName);
    ProjectContextDTO getProjectContext(String username, String timestamp, String adaptorName);
}
