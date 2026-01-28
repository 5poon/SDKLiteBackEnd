package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface MetadataSerializerService {
    void serializeCounters(List<CounterDef> counters, Writer writer) throws IOException;
    void serializeMocs(List<MocDef> mocs, Writer writer) throws IOException;
}
