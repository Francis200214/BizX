package com.demo.test;

import com.biz.cache.map.TimedDeletionMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

/**
 * @author francis
 * @create 2024-07-31 15:16
 **/
@Slf4j
public class TimedDeletionMapTest {

    private TimedDeletionMap<String, String> timedDeletionMap;
    private ConcurrentHashMap<String, TimedDeletionMap.TimedEntry<String>> mockMap;
    private ScheduledExecutorService mockExecutorService;

    @BeforeEach
    void setUp() {
        mockMap = spy(new ConcurrentHashMap<>());
        mockExecutorService = Executors.newSingleThreadScheduledExecutor();
        timedDeletionMap = new TimedDeletionMap<>(mockMap, mockExecutorService, null, 1, TimeUnit.MILLISECONDS);
        log.info("Setup complete");
    }

    @AfterEach
    void tearDown() throws Exception {
        timedDeletionMap.close();
        log.info("TimedDeletionMap closed");
    }

    @Test
    void testPutAndGet() {
        String key = "key1";
        String value = "value1";
        timedDeletionMap.put(key, value, 5, TimeUnit.SECONDS);
        log.info("Put key: {}, value: {}", key, value);

        String retrievedValue = timedDeletionMap.get(key);
        log.info("Get key: {}, returned value: {}", key, retrievedValue);
        assertEquals(value, retrievedValue);
    }

    @Test
    void testExpiration() throws InterruptedException {
        String key = "key1";
        String value = "value1";
        timedDeletionMap.put(key, value, 1, TimeUnit.SECONDS);
        log.info("Put key: {}, value: {}, expires in 1 second", key, value);

        log.info("Get key: {}, returned value after expiration: {}", key, timedDeletionMap.get(key));

        Thread.sleep(2000); // 等待条目过期
        log.info("Waited 2 seconds for the entry to expire");

        String retrievedValue = timedDeletionMap.get(key);
        log.info("Get key: {}, returned value after expiration: {}", key, retrievedValue);
        assertNull(retrievedValue);
    }

    @Test
    void testRemove() {
        String key = "key1";
        String value = "value1";
        timedDeletionMap.put(key, value, 5, TimeUnit.SECONDS);
        log.info("Put key: {}, value: {}", key, value);

        String removedValue = timedDeletionMap.remove(key);
        log.info("Removed key: {}, returned value: {}", key, removedValue);
        assertEquals(value, removedValue);

        String retrievedValue = timedDeletionMap.get(key);
        log.info("Get key: {} after removal, returned value: {}", key, retrievedValue);
        assertNull(retrievedValue);
    }

}
