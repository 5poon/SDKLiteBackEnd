package com.sdklite.backend.service;

import com.sdklite.backend.dto.AdaptorInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdaptorDiscoveryServiceImplTest {

    @TempDir
    Path tempDir;

    private AdaptorDiscoveryService discoveryService;

    @BeforeEach
    void setUp() throws IOException {
        // Create dummy structure: vendor/tech/name/version/name.iar
        Path adaptorPath = tempDir.resolve("adaptors/nkia/gnodeb/nkia_gnodeb_csv_nr25r2/03.01.01");
        Files.createDirectories(adaptorPath);
        Files.createFile(adaptorPath.resolve("nkia_gnodeb_csv_nr25r2.iar"));

        discoveryService = new AdaptorDiscoveryServiceImpl(tempDir.toString());
    }

    @Test
    void listAllAdaptors_ShouldReturnList() {
        List<AdaptorInfo> adaptors = discoveryService.listAllAdaptors();
        
        assertEquals(1, adaptors.size());
        AdaptorInfo info = adaptors.get(0);
        assertEquals("nkia", info.getVendor());
        assertEquals("gnodeb", info.getTechnology());
        assertEquals("nkia_gnodeb_csv_nr25r2", info.getName());
        assertEquals("03.01.01", info.getVersion());
        assertTrue(info.getPath().endsWith("nkia_gnodeb_csv_nr25r2.iar"));
    }

    @Test
    void listAllAdaptors_ShouldReturnEmptyList_WhenNoAdaptors() throws IOException {
        Path emptyDir = tempDir.resolve("empty");
        Files.createDirectories(emptyDir);
        discoveryService = new AdaptorDiscoveryServiceImpl(emptyDir.toString());
        
        List<AdaptorInfo> adaptors = discoveryService.listAllAdaptors();
        assertTrue(adaptors.isEmpty());
    }
}
