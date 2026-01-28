package com.sdklite.backend.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;

@Service
public class AdaptorExtractionServiceImpl implements AdaptorExtractionService {

    private final Path adaptorsRoot;
    private final FileService fileService;
    private final LockService lockService;

    public AdaptorExtractionServiceImpl(@Value("${app.file.root-path:example}") String rootPath,
                                        FileService fileService,
                                        LockService lockService) {
        this.adaptorsRoot = Paths.get(rootPath).resolve("adaptors").toAbsolutePath().normalize();
        this.fileService = fileService;
        this.lockService = lockService;
    }

    @Override
    public void extractAdaptor(String vendor, String technology, String name, String version, String username, String timestamp) throws IOException {
        String adaptorId = String.format("%s/%s/%s/%s", vendor, technology, name, version);
        
        // 1. Acquire Lock
        lockService.acquireLock(adaptorId, username);

        try {
            // 2. Resolve Source IAR Path
            Path iarPath = adaptorsRoot.resolve(adaptorId).resolve(name + ".iar");
            if (!Files.exists(iarPath)) {
                throw new IOException("Adaptor IAR file not found: " + iarPath);
            }

            // 3. Resolve User Temp Config Path
            Path userConfigDir = fileService.resolveUserTempPath(username, timestamp).resolve("config");
            Files.createDirectories(userConfigDir);

            // 4. Extract IAR (ZIP)
            try (ZipFile zipFile = new ZipFile(iarPath.toFile())) {
                Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
                while (entries.hasMoreElements()) {
                    ZipArchiveEntry entry = entries.nextElement();
                    Path entryPath = userConfigDir.resolve(entry.getName()).normalize();
                    
                    // Basic path traversal guard during extraction (though we trust our IARs)
                    if (!entryPath.startsWith(userConfigDir)) {
                        continue; 
                    }

                    if (entry.isDirectory()) {
                        Files.createDirectories(entryPath);
                    } else {
                        Files.createDirectories(entryPath.getParent());
                        try (InputStream is = zipFile.getInputStream(entry)) {
                            Files.copy(is, entryPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Rollback lock on failure if necessary? 
            // For now, let's keep the lock logic simple: acquire -> success/fail -> user handles release explicitly or via cleanup.
            // But ideally on exception we might want to release if it was just acquired? 
            // Spec says "lock released when holding user explicitly closes session". 
            // So we leave the lock held so user can see it failed and retry or close.
            throw e;
        }
    }
}
