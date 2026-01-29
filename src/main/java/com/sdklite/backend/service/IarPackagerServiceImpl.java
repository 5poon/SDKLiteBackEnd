package com.sdklite.backend.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class IarPackagerServiceImpl implements IarPackagerService {

    @Override
    public void packageConfig(Path sourceDir, Path targetIarPath) throws IOException {
        Files.createDirectories(targetIarPath.getParent());
        
        try (ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(Files.newOutputStream(targetIarPath))) {
            try (Stream<Path> paths = Files.walk(sourceDir)) {
                paths.filter(path -> !Files.isDirectory(path))
                        .forEach(path -> {
                            String entryName = sourceDir.relativize(path).toString().replace("\\", "/");
                            ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
                            try {
                                zaos.putArchiveEntry(entry);
                                Files.copy(path, zaos);
                                zaos.closeArchiveEntry();
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to package entry: " + entryName, e);
                            }
                        });
            }
        }
    }
}
