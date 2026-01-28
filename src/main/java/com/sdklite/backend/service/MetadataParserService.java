package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface MetadataParserService {
    List<CounterDef> parseCounters(Reader reader) throws IOException;
    List<MocDef> parseMocs(Reader reader) throws IOException;
}
