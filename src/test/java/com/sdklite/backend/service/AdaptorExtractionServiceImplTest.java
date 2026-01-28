package com.sdklite.backend.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class AdaptorExtractionServiceImplTest {

    @TempDir
    Path tempDir;

    private AdaptorExtractionService extractionService;
    private LockService lockService;
    private FileService fileService;

    @BeforeEach
    void setUp() throws IOException {
        fileService = new FileServiceImpl(tempDir.toString());
        lockService = new LockServiceImpl();
        extractionService = new AdaptorExtractionServiceImpl(tempDir.toString(), fileService, lockService);

        // Setup dummy IAR
        Path adaptorDir = tempDir.resolve("adaptors/vendor1/tech1/name1/v1");
        Files.createDirectories(adaptorDir);
        Path iarFile = adaptorDir.resolve("name1.iar");
        
        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(iarFile.toFile())) {
            ZipArchiveEntry entry = new ZipArchiveEntry("metadata/test.txt");
            zipOut.putArchiveEntry(entry);
            zipOut.write("test content".getBytes());
            zipOut.closeArchiveEntry();
        }
    }

    @Test
    void extractAdaptor_ShouldExtractFiles_AndAcquireLock() throws IOException {
        String username = "user1";
        String timestamp = "12345";

        extractionService.extractAdaptor("vendor1", "tech1", "name1", "v1", username, timestamp);

        // Verify Lock
        assertTrue(lockService.checkLock("vendor1/tech1/name1/v1").isPresent());
        assertEquals(username, lockService.checkLock("vendor1/tech1/name1/v1").get().getUsername());

        // Verify File Extraction
        Path extractedFile = tempDir.resolve("user_temp/" + username + "/" + timestamp + "/config/metadata/test.txt");
        assertTrue(Files.exists(extractedFile));
        assertEquals("test content", Files.readString(extractedFile));
    }

    @Test
    void extractAdaptor_ShouldThrowException_WhenAlreadyLocked() {
        lockService.acquireLock("vendor1/tech1/name1/v1", "user2");

        assertThrows(IllegalStateException.class, () -> 
            extractionService.extractAdaptor("vendor1", "tech1", "name1", "v1", "user1", "12345")
        );
    }
}
