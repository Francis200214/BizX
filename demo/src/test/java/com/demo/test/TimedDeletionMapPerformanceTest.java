package com.demo.test;

import com.biz.cache.map.TimedDeletionMap;
import com.biz.common.concurrent.ExecutorsUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 性能测试类，用于测试 TimedDeletionMap 在高并发场景下的性能。
 * 包含并发执行 put、get 和 remove 操作的测试。
 *
 * @author francis
 * @since 1.0.1
 **/
@Slf4j
public class TimedDeletionMapPerformanceTest {

    private TimedDeletionMap<String, String> timedDeletionMap;
    private ScheduledExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = ExecutorsUtils.buildScheduledExecutorService();
        timedDeletionMap = new TimedDeletionMap<>(new ConcurrentHashMap<>(), executorService, null, 100, TimeUnit.MILLISECONDS);
        log.info("Setup complete");
    }

    @AfterEach
    void tearDown() throws Exception {
        timedDeletionMap.close();
        log.info("TimedDeletionMap closed");
    }

    @Test
    void testCustomInsertionPattern() throws InterruptedException {
        int initialInsertions = 1000;

        log.info("Starting initial insertions of {} entries", initialInsertions);
        long startTime = System.nanoTime(); // 开始计时
        long memoryBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); // 记录内存使用

        // 初始阶段：每1毫秒插入1条，共插入100000条
        for (int i = 0; i < initialInsertions; i++) {
            String key = "initial-" + i;
            timedDeletionMap.put(key, "value", 100, TimeUnit.MILLISECONDS);
            if (i % 10 == 0) {
                long currentMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                log.info("Memory usage at initial insertion {}: {} MB", i, currentMemory / (1024 * 1024));
                log.info("Map Size at initial insertion {}: {}", i, timedDeletionMap.size());
                Thread.sleep(100); // 每插入1000条暂停1毫秒
            }
        }

        // 添加延迟以确保 CleanupTask 有时间运行
        Thread.sleep(200);

        long endTime = System.nanoTime(); // 结束计时
        long memoryAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); // 记录内存使用

        log.info("Custom insertion pattern test completed");
        log.info("Total execution time: {} ms", (endTime - startTime) / 1_000_000);
        log.info("Memory usage before: {} MB", memoryBefore / (1024 * 1024));
        log.info("Memory usage after: {} MB", memoryAfter / (1024 * 1024));
        log.info("Net memory usage: {} MB", (memoryAfter - memoryBefore) / (1024 * 1024));
        log.info("Final Map Size: {}", timedDeletionMap.size());
    }
}
