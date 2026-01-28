package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import com.sdklite.backend.model.MocAttributeDef;

import java.io.IOException;
import java.util.List;

public interface WorkAreaService {
    void saveCounters(String username, String timestamp, String adaptorName, List<CounterDef> counters) throws IOException;
    void saveMocs(String username, String timestamp, String adaptorName, List<MocDef> mocs) throws IOException;
    void saveAttributes(String username, String timestamp, String adaptorName, List<MocAttributeDef> attributes) throws IOException;
}
