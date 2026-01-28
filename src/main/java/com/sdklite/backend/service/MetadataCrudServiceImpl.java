package com.sdklite.backend.service;

import com.sdklite.backend.model.CounterDef;
import com.sdklite.backend.model.MocAttributeDef;
import com.sdklite.backend.security.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class MetadataCrudServiceImpl implements MetadataCrudService {

    private final FileService fileService;
    private final MetadataParserService parserService;
    private final WorkAreaService workAreaService;
    private final String usersFilePath;
    private final ObjectMapper objectMapper;

    public MetadataCrudServiceImpl(FileService fileService, 
                                   MetadataParserService parserService, 
                                   WorkAreaService workAreaService,
                                   @Value("${app.security.users-file}") String usersFilePath,
                                   ObjectMapper objectMapper) {
        this.fileService = fileService;
        this.parserService = parserService;
        this.workAreaService = workAreaService;
        this.usersFilePath = usersFilePath;
        this.objectMapper = objectMapper;
    }

    private void enforceQuota(String username, int currentCount, String type) throws IOException {
        List<User> users = objectMapper.readValue(new File(usersFilePath), new TypeReference<List<User>>(){});
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        Integer max = user.getQuotas().get(type);
        if (max != null && currentCount >= max) {
            throw new IllegalStateException("Quota exceeded: Maximum " + type + " allowed is " + max);
        }
    }

    private List<CounterDef> loadCounters(String username, String timestamp, String adaptorName) throws IOException {
        Path path = fileService.resolveUserTempPath(username, timestamp)
                .resolve("config/metadata")
                .resolve(adaptorName)
                .resolve("counter_def_ref.txt");
        
        try (FileReader reader = new FileReader(path.toFile())) {
            return parserService.parseCounters(reader);
        }
    }

    private List<MocAttributeDef> loadAttributes(String username, String timestamp, String adaptorName) throws IOException {
        Path path = fileService.resolveUserTempPath(username, timestamp)
                .resolve("config/metadata")
                .resolve(adaptorName)
                .resolve("moc_attribute_def_ref.txt");
        
        try (FileReader reader = new FileReader(path.toFile())) {
            return parserService.parseAttributes(reader);
        }
    }

    @Override
    public List<CounterDef> createCounter(String username, String timestamp, String adaptorName, CounterDef newCounter) throws IOException {
        List<CounterDef> counters = loadCounters(username, timestamp, adaptorName);
        enforceQuota(username, counters.size(), "counters");
        
        counters.add(newCounter);
        workAreaService.saveCounters(username, timestamp, adaptorName, counters);
        return counters;
    }

    @Override
    public List<CounterDef> updateCounter(String username, String timestamp, String adaptorName, String counterId, CounterDef updatedCounter) throws IOException {
        List<CounterDef> counters = loadCounters(username, timestamp, adaptorName);
        for (int i = 0; i < counters.size(); i++) {
            if (counters.get(i).getId().equals(counterId)) {
                counters.set(i, updatedCounter);
                workAreaService.saveCounters(username, timestamp, adaptorName, counters);
                return counters;
            }
        }
        throw new IllegalArgumentException("Counter not found: " + counterId);
    }

    @Override
    public List<CounterDef> deleteCounter(String username, String timestamp, String adaptorName, String counterId) throws IOException {
        List<CounterDef> counters = loadCounters(username, timestamp, adaptorName);
        boolean removed = counters.removeIf(c -> c.getId().equals(counterId));
        if (removed) {
            workAreaService.saveCounters(username, timestamp, adaptorName, counters);
            return counters;
        }
        throw new IllegalArgumentException("Counter not found: " + counterId);
    }

    @Override
    public void createAttribute(String username, String timestamp, String adaptorName, MocAttributeDef newAttr) throws IOException {
        List<MocAttributeDef> attrs = loadAttributes(username, timestamp, adaptorName);
        enforceQuota(username, attrs.size(), "attributes");
        attrs.add(newAttr);
        workAreaService.saveAttributes(username, timestamp, adaptorName, attrs);
    }

    @Override
    public void updateAttribute(String username, String timestamp, String adaptorName, String attrId, MocAttributeDef updatedAttr) throws IOException {
        List<MocAttributeDef> attrs = loadAttributes(username, timestamp, adaptorName);
        for (int i = 0; i < attrs.size(); i++) {
            if (attrs.get(i).getId().equals(attrId)) {
                attrs.set(i, updatedAttr);
                workAreaService.saveAttributes(username, timestamp, adaptorName, attrs);
                return;
            }
        }
        throw new IllegalArgumentException("Attribute not found: " + attrId);
    }

    @Override
    public void deleteAttribute(String username, String timestamp, String adaptorName, String attrId) throws IOException {
        List<MocAttributeDef> attrs = loadAttributes(username, timestamp, adaptorName);
        if (attrs.removeIf(a -> a.getId().equals(attrId))) {
            workAreaService.saveAttributes(username, timestamp, adaptorName, attrs);
        } else {
            throw new IllegalArgumentException("Attribute not found: " + attrId);
        }
    }
}
