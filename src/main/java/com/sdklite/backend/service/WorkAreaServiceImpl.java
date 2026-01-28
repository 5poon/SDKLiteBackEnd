package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocDef;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;

@Service
public class WorkAreaServiceImpl implements WorkAreaService {

    private final FileService fileService;
    private final MetadataSerializerService serializerService;

    public WorkAreaServiceImpl(FileService fileService, MetadataSerializerService serializerService) {
        this.fileService = fileService;
        this.serializerService = serializerService;
    }

    @Override
    public void saveCounters(String username, String timestamp, String adaptorName, List<CounterDef> counters) throws IOException {
        Path path = fileService.resolveUserTempPath(username, timestamp)
                .resolve("config/metadata")
                .resolve(adaptorName)
                .resolve("counter_def_ref.txt");
        
        StringWriter writer = new StringWriter();
        serializerService.serializeCounters(counters, writer);
        fileService.atomicWrite(path, writer.toString());
    }

    @Override
    public void saveMocs(String username, String timestamp, String adaptorName, List<MocDef> mocs) throws IOException {
        Path path = fileService.resolveUserTempPath(username, timestamp)
                .resolve("config/metadata/nml")
                .resolve(adaptorName)
                .resolve("moc_def_ref.txt"); // Adjusting based on common pattern observed earlier
        
        StringWriter writer = new StringWriter();
        serializerService.serializeMocs(mocs, writer);
        fileService.atomicWrite(path, writer.toString());
    }
}
