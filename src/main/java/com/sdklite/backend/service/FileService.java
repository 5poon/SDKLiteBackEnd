package com.sdklite.backend.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    Path resolveUserTempPath(String username, String timestamp);
    void atomicWrite(Path path, byte[] content) throws IOException;
    void atomicWrite(Path path, String content) throws IOException;
    void validatePath(Path path);
}
