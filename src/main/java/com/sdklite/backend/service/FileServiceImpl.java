package com.sdklite.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private final Path rootPath;

    public FileServiceImpl(@Value("${app.file.root-path:example}") String rootPath) {
        this.rootPath = Paths.get(rootPath).toAbsolutePath().normalize();
    }

    @Override
    public Path resolveUserTempPath(String username, String timestamp) {
        Path path = rootPath.resolve("user_temp").resolve(username).resolve(timestamp);
        validatePath(path);
        return path;
    }

    @Override
    public void atomicWrite(Path path, byte[] content) throws IOException {
        validatePath(path);
        Path tempFile = Files.createTempFile(path.getParent(), "tmp_", ".tmp");
        try {
            Files.write(tempFile, content);
            Files.move(tempFile, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Files.deleteIfExists(tempFile);
            throw e;
        }
    }

    @Override
    public void atomicWrite(Path path, String content) throws IOException {
        atomicWrite(path, content.getBytes());
    }

    @Override
    public void validatePath(Path path) {
        Path normalizedPath = path.normalize();
        if (!normalizedPath.startsWith(rootPath)) {
            throw new SecurityException("Access denied: Path is outside the root directory: " + path);
        }
    }
}
