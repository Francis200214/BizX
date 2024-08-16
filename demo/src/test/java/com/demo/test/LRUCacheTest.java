package com.demo.test;


import com.biz.cache.map.ThreadSafeLRUCache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LRUCacheTest {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个LRUCache实例，设置最大缓存大小为100
        ThreadSafeLRUCache<String, Integer> cache = new ThreadSafeLRUCache<>(100);

        // 创建一个线程池，线程数为10000
        ExecutorService executorService = Executors.newFixedThreadPool(10000);

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        // 提交10000个任务到线程池，每个任务都会对LRUCache进行操作
        for (int i = 0; i < 10000; i++) {
            final int index = i;
            executorService.submit(() -> {
                String key = "key-" + index;
                Integer value = index;
                // 将数据放入缓存
                cache.put(key, value);
                // 从缓存中读取数据
                Integer cachedValue = cache.get(key);
                if (cachedValue != null && cachedValue != value) {
                    System.err.println("Cache value mismatch! Expected: " + value + ", but got: " + cachedValue);
                }
            });
        }

        // 关闭线程池，并等待所有任务完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // 记录结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("Test completed in " + (endTime - startTime) + " ms");

        // 打印缓存的最终大小，检查是否符合预期
        System.out.println("Final cache size: " + cache.size());
    }


}
