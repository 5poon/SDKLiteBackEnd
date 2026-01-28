package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import java.io.IOException;
import java.util.List;

public interface MetadataCrudService {
    List<CounterDef> createCounter(String username, String timestamp, String adaptorName, CounterDef newCounter) throws IOException;
    List<CounterDef> updateCounter(String username, String timestamp, String adaptorName, String counterId, CounterDef updatedCounter) throws IOException;
    List<CounterDef> deleteCounter(String username, String timestamp, String adaptorName, String counterId) throws IOException;
}
