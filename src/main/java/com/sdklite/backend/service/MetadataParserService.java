package com.sdklite.backend.service;

import com.sdklite.backend.model.*;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface MetadataParserService {
    List<CounterDef> parseCounters(Reader reader) throws IOException;
    List<MocDef> parseMocs(Reader reader) throws IOException;
    
    List<ImportDataSource> parseDataSources(Reader reader) throws IOException;
    List<NeImportEntity> parseNeEntities(Reader reader) throws IOException;
    List<CounterImportEntity> parseCounterEntities(Reader reader) throws IOException;
    List<AttrImportEntity> parseAttrEntities(Reader reader) throws IOException;
    
    List<MocDefParent> parseMocParents(Reader reader) throws IOException;
    List<VendorMocDef> parseVendorMocs(Reader reader) throws IOException;
    
    List<MocAttributeDef> parseAttributes(Reader reader) throws IOException;
    List<ImportAttrFor> parseAttributeMappings(Reader reader) throws IOException;
}
