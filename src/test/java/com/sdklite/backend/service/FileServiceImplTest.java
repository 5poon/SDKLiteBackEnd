package com.sdklite.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceImplTest {

    @TempDir
    Path tempDir;

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl(tempDir.toString());
    }

    @Test
    void resolveUserTempPath_ShouldReturnCorrectPath() {
        Path path = fileService.resolveUserTempPath("user1", "123456789");
        assertTrue(path.toString().contains("user_temp"));
        assertTrue(path.toString().contains("user1"));
        assertTrue(path.toString().contains("123456789"));
        assertTrue(path.startsWith(tempDir));
    }

    @Test
    void atomicWrite_ShouldWriteContentFile() throws IOException {
        Path targetDir = tempDir.resolve("user_temp/user1/123456789");
        Files.createDirectories(targetDir);
        Path targetFile = targetDir.resolve("test.txt");

        String content = "Hello World";
        fileService.atomicWrite(targetFile, content);

        assertTrue(Files.exists(targetFile));
        assertEquals(content, Files.readString(targetFile));
    }

    @Test
    void validatePath_ShouldThrowException_WhenPathIsOutsideRoot() {
        Path outsidePath = tempDir.resolve("../secret.txt").normalize();
        assertThrows(SecurityException.class, () -> fileService.validatePath(outsidePath));
    }
}
