package com.sdklite.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class LockServiceImplTest {

    private LockService lockService;

    @BeforeEach
    void setUp() {
        lockService = new LockServiceImpl();
    }

    @Test
    void acquireLock_ShouldSucceed_WhenNoLockExists() {
        lockService.acquireLock("adaptor1", "user1");
        Optional<Lock> lock = lockService.checkLock("adaptor1");
        
        assertTrue(lock.isPresent());
        assertEquals("user1", lock.get().getUsername());
    }

    @Test
    void acquireLock_ShouldThrowException_WhenLockedByAnotherUser() {
        lockService.acquireLock("adaptor1", "user1");
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            lockService.acquireLock("adaptor1", "user2")
        );
        
        assertEquals("Adaptor is locked by user: user1", exception.getMessage());
    }

    @Test
    void acquireLock_ShouldSucceed_WhenLockedBySameUser() {
        lockService.acquireLock("adaptor1", "user1");
        assertDoesNotThrow(() -> lockService.acquireLock("adaptor1", "user1"));
    }

    @Test
    void releaseLock_ShouldRemoveLock_WhenOwnerReleases() {
        lockService.acquireLock("adaptor1", "user1");
        lockService.releaseLock("adaptor1", "user1");
        
        Optional<Lock> lock = lockService.checkLock("adaptor1");
        assertFalse(lock.isPresent());
    }

    @Test
    void releaseLock_ShouldNotRemoveLock_WhenNonOwnerReleases() {
        lockService.acquireLock("adaptor1", "user1");
        lockService.releaseLock("adaptor1", "user2");
        
        Optional<Lock> lock = lockService.checkLock("adaptor1");
        assertTrue(lock.isPresent());
        assertEquals("user1", lock.get().getUsername());
    }

    @Test
    void concurrentAccess_ShouldOnlyAllowOneUser() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        String adaptorId = "concurrentAdaptor";

        for (int i = 0; i < numberOfThreads; i++) {
            final String user = "user" + i;
            service.submit(() -> {
                try {
                    lockService.acquireLock(adaptorId, user);
                    successCount.incrementAndGet();
                } catch (IllegalStateException e) {
                    // Expected for all but one
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        assertEquals(1, successCount.get());
    }
}
