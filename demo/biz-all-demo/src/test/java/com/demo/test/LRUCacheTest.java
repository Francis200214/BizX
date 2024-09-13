package com.demo.test;

import com.biz.cache.map.ThreadSafeLRUCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class LRUCacheTest {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个LRUCache实例，设置最大缓存大小为100
        ThreadSafeLRUCache<String, Integer> cache = new ThreadSafeLRUCache<>(100);

        // 创建一个线程池，线程数为32（根据系统配置调整）
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 提交1000000个任务到线程池，每个任务都会对LRUCache进行操作
        for (int i = 0; i < 1000000; i++) {
            final int index = i;
            executorService.submit(() -> {
                String key = "key-" + index;
                Integer value = index;
                // 将数据放入缓存
                cache.put(key, value);
                // 从缓存中读取数据
                Integer cachedValue = cache.get(key);
                if (cachedValue != null && !cachedValue.equals(value)) {
                    System.err.println("Cache value mismatch! Expected: " + value + ", but got: " + cachedValue);
                }
            });
        }

        // 定期打印内存使用情况和GC信息
        ScheduledExecutorService memoryMonitor = Executors.newScheduledThreadPool(1);
        memoryMonitor.scheduleAtFixedRate(() -> {
            printMemoryUsage();
            printGCInfo();
        }, 0, 10, TimeUnit.SECONDS);

        // 关闭线程池，并等待所有任务完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // 关闭监控线程池
        memoryMonitor.shutdown();
        memoryMonitor.awaitTermination(1, TimeUnit.MINUTES);

        // 记录结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("Test completed in " + (endTime - startTime) + " ms");

        // 打印缓存的最终大小，检查是否符合预期
        System.out.println("Final cache size: " + cache.size());
    }

    /**
     * 打印当前内存使用情况
     */
    private static void printMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory: Used=" + heapMemoryUsage.getUsed() + " bytes, Committed=" +
                heapMemoryUsage.getCommitted() + " bytes, Max=" + heapMemoryUsage.getMax() + " bytes");
        System.out.println("Non-Heap Memory: Used=" + nonHeapMemoryUsage.getUsed() + " bytes, Committed=" +
                nonHeapMemoryUsage.getCommitted() + " bytes, Max=" + nonHeapMemoryUsage.getMax() + " bytes");
    }

    /**
     * 打印GC信息
     */
    private static void printGCInfo() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("GC Name: " + gcBean.getName() + ", Collections: " +
                    gcBean.getCollectionCount() + ", Collection Time: " +
                    gcBean.getCollectionTime() + " ms");
        }
    }
}
