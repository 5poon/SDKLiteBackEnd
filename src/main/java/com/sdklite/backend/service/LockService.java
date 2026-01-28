package com.sdklite.backend.service;

import java.util.Optional;

public interface LockService {
    void acquireLock(String adaptorId, String username) throws IllegalStateException;
    void releaseLock(String adaptorId, String username);
    Optional<Lock> checkLock(String adaptorId);
}
