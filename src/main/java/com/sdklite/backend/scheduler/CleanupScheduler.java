package com.sdklite.backend.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Component
public class CleanupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CleanupScheduler.class);

    private final Path userTempRoot;
    private final int ttlDays;

    public CleanupScheduler(@Value("${app.file.root-path:example}") String rootPath,
                            @Value("${app.cleanup.ttl-days:7}") int ttlDays) {
        this.userTempRoot = Paths.get(rootPath).resolve("user_temp").toAbsolutePath().normalize();
        this.ttlDays = ttlDays;
    }

    @Scheduled(cron = "${app.cleanup.cron:0 0 0 * * *}")
    public void cleanupOldSessions() {
        logger.info("Starting automated cleanup of sessions older than {} days", ttlDays);
        if (!Files.exists(userTempRoot)) {
            return;
        }

        Instant threshold = Instant.now().minus(ttlDays, ChronoUnit.DAYS);

        try (Stream<Path> userStream = Files.list(userTempRoot)) {
            userStream.filter(Files::isDirectory).forEach(userPath -> {
                try (Stream<Path> sessionStream = Files.list(userPath)) {
                    sessionStream.filter(Files::isDirectory).forEach(sessionPath -> {
                        try {
                            String timestampStr = sessionPath.getFileName().toString();
                            long timestamp = Long.parseLong(timestampStr);
                            Instant sessionTime = Instant.ofEpochMilli(timestamp);

                            if (sessionTime.isBefore(threshold)) {
                                logger.info("Purging stale session: {}", sessionPath);
                                FileSystemUtils.deleteRecursively(sessionPath);
                                
                                // Optional: Check if user dir is now empty and delete it too
                                try (Stream<Path> s = Files.list(userPath)) {
                                    if (s.findAny().isEmpty()) {
                                        Files.delete(userPath);
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                            logger.warn("Skipping directory with invalid timestamp format: {}", sessionPath);
                        } catch (IOException e) {
                            logger.error("Failed to delete session: {}", sessionPath, e);
                        }
                    });
                } catch (IOException e) {
                    logger.error("Failed to list sessions for user: {}", userPath, e);
                }
            });
        } catch (IOException e) {
            logger.error("Failed to scan user_temp root", e);
        }
    }
}
