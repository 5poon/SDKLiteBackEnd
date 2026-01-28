package com.sdklite.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LockServiceImpl implements LockService {

    private final ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();

    @Override
    public void acquireLock(String adaptorId, String username) throws IllegalStateException {
        locks.compute(adaptorId, (key, existingLock) -> {
            if (existingLock != null) {
                if (existingLock.getUsername().equals(username)) {
                    return existingLock; // Re-entrant for same user
                }
                throw new IllegalStateException("Adaptor is locked by user: " + existingLock.getUsername());
            }
            return new Lock(adaptorId, username, Instant.now());
        });
    }

    @Override
    public void releaseLock(String adaptorId, String username) {
        locks.computeIfPresent(adaptorId, (key, existingLock) -> {
            if (existingLock.getUsername().equals(username)) {
                return null; // Release lock
            }
            return existingLock; // Not the owner, ignore
        });
    }

    @Override
    public Optional<Lock> checkLock(String adaptorId) {
        return Optional.ofNullable(locks.get(adaptorId));
    }
}
