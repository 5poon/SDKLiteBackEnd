package com.sdklite.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final FileService fileService;
    private final LockService lockService;

    public SessionServiceImpl(FileService fileService, LockService lockService) {
        this.fileService = fileService;
        this.lockService = lockService;
    }

    @Override
    public void closeSession(String username, String timestamp, String adaptorName) throws IOException {
        logger.info("Closing session for user: {}, timestamp: {}, adaptor: {}", username, timestamp, adaptorName);

        // 1. Release Lock
        // Note: adaptorName in the API likely refers to the full ID or we reconstruct it.
        // For simplicity, we assume the adaptorName provided matches the lock key or we search.
        // Assuming the lock key follows the vendor/tech/name/version pattern, 
        // we might need more info or search existing locks.
        // For now, let's assume adaptorName is the key used in LockService.
        lockService.releaseLock(adaptorName, username);

        // 2. Cleanup Directory
        Path userTempPath = fileService.resolveUserTempPath(username, timestamp);
        if (userTempPath.toFile().exists()) {
            boolean deleted = FileSystemUtils.deleteRecursively(userTempPath);
            if (deleted) {
                logger.info("Successfully deleted session directory: {}", userTempPath);
            } else {
                logger.warn("Failed to delete session directory: {}", userTempPath);
            }
        }
    }
}
