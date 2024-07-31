package com.biz.cache.map;

import com.biz.common.concurrent.ExecutorsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * 提供定时删除功能的并发映射表。
 * 该类封装了一个ConcurrentHashMap，并通过定时任务定期删除过期的条目。
 *
 * @param <K> Map 中键的类型
 * @param <V> Map 中值的类型
 * @author francis
 * @create: 2023-04-23 17:14
 */
@Slf4j
public class TimedDeletionMap<K, V> implements AutoCloseable {

    /**
     * 使用ConcurrentHashMap来存储键值对，其中值是一个带有过期时间的条目。
     * 选择ConcurrentHashMap是因为需要线程安全的映射，同时能够高效地并发访问和操作。
     */
    private final ConcurrentHashMap<K, TimedEntry<V>> map;

    /**
     * 使用ScheduledExecutorService来定期执行清理任务。
     * 它负责轮询映射并移除过期的条目，以保持映射的大小和性能。
     */
    private final ScheduledExecutorService executorService;

    /**
     * 清理间隔定义了多久执行一次清理任务。
     * 这个间隔帮助控制清理频率，避免过于频繁或过于稀疏的清理操作。
     */
    private final long cleanupInterval;

    /**
     * timeUnit定义了清理间隔的时间单位，允许灵活配置清理任务的调度。
     * 它支持不同的时间单位，如秒、分钟、小时等，以适应不同的应用场景。
     */
    private final TimeUnit timeUnit;

    /**
     * 当元素被清除时执行的函数。
     */
    private final Consumer<V> onRemoveConsumer;

    /**
     * 使用给定的映射、执行器服务、清理间隔和时间单位初始化。
     *
     * @param map              存储条目的映射
     * @param executorService  用于调度清理任务的执行器服务
     * @param onRemoveConsumer 当元素被清除时执行的函数
     * @param cleanupInterval  清理任务执行的间隔
     * @param timeUnit         清理任务执行间隔的时间单位
     */
    public TimedDeletionMap(ConcurrentHashMap<K, TimedEntry<V>> map, ScheduledExecutorService executorService, Consumer<V> onRemoveConsumer, long cleanupInterval, TimeUnit timeUnit) {
        this.map = map;
        this.executorService = executorService;
        // 初始化清理任务
        this.cleanupInterval = cleanupInterval;
        this.timeUnit = timeUnit;
        this.onRemoveConsumer = onRemoveConsumer;
        scheduleCleanupTask(cleanupInterval, timeUnit);
    }

    /**
     * 向映射中添加一个键值对，并指定其过期时间。
     *
     * @param key            键
     * @param value          值
     * @param expirationTime 过期时间
     * @param timeUnit       时间单位
     * @return 添加的值
     */
    public V put(K key, V value, long expirationTime, TimeUnit timeUnit) {
        long expirationMillis = System.currentTimeMillis() + timeUnit.toMillis(expirationTime);
        TimedEntry<V> timedEntry = new TimedEntry<>(value, expirationMillis);
        map.put(key, timedEntry);
        return timedEntry.getValue();
    }

    /**
     * 获取指定键的值，如果已过期，则返回null。
     *
     * @param key 键
     * @return 值或null（如果已过期）
     */
    public V get(K key) {
        TimedEntry<V> entry = map.get(key);
        if (entry != null && !isExpired(entry)) {
            return entry.getValue();
        }
        return null;
    }

    /**
     * 移除指定键的条目，并返回其值（如果存在）。
     *
     * @param key 键
     * @return 被移除条目的值或null（如果键不存在）
     */
    public V remove(K key) {
        TimedEntry<V> entry = map.remove(key);
        return entry != null ? entry.getValue() : null;
    }

    /**
     * 获取映射中条目的数量。
     *
     * @return 映射中条目的数量
     */
    public long size() {
        return map.size();
    }

    /**
     * 关闭映射，包括停止执行器服务。
     *
     * @throws Exception 如果关闭执行器服务时发生错误
     */
    @Override
    public void close() throws Exception {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    /**
     * 提供一个构建器模式来创建 TimedDeletionMap 实例。
     *
     * @param <K> Map 中键的类型
     * @param <V> Map 中值的类型
     */
    public static class TimedDeletionMapBuilder<K, V> {
        /**
         * 供应商接口，用于提供ConcurrentHashMap实例。
         * 这个字段被用来延迟初始化ConcurrentHashMap，以提高内存使用效率。
         * ConcurrentHashMap用于存储具有时间限制的条目，键为K类型，值为TimedEntry<V>类型。
         */
        private Supplier<ConcurrentHashMap<K, TimedEntry<V>>> mapSupplier;

        /**
         * 定时任务执行服务，用于定期执行清理任务。
         * 这个 executorService 负责定时清理过期的条目，以保持缓存的活力。
         * 使用 ExecutorsUtils.buildScheduledExecutorService() 进行初始化，该方法具体实现了 executorService 的配置和创建。
         */
        private ScheduledExecutorService executorService = ExecutorsUtils.buildScheduledExecutorService();

        /**
         * 清理间隔时间。
         * 定义了清理任务之间的时间间隔，用于控制缓存的清理频率。
         * 单位为毫秒，默认值为100毫秒，表示清理任务将每100毫秒执行一次。
         */
        private long cleanupInterval = 100;

        /**
         * 时间单位。
         * 用于指定 cleanupInterval 的时间单位。
         * 默认单位为毫秒，表示清理间隔以毫秒为单位进行计算。
         */
        private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        /**
         * 当元素被清除时执行的函数。
         */
        private Consumer<V> onRemoveConsumer = value -> {
        };

        /**
         * 指定用于存储条目的 ConcurrentHashMap 的 Supplier。
         *
         * @param supplier ConcurrentHashMap 的 Supplier
         * @return 当前构建器实例
         */
        public TimedDeletionMapBuilder<K, V> withMapSupplier(Supplier<ConcurrentHashMap<K, TimedEntry<V>>> supplier) {
            this.mapSupplier = supplier;
            return this;
        }

        /**
         * 指定用于清除数据的定时任务线程池。
         *
         * @param executorService 清除数据的定时任务线程池
         * @return 当前构建器实例
         */
        public TimedDeletionMapBuilder<K, V> withExecutorService(ScheduledExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        /**
         * 设置清理任务的执行间隔。
         *
         * @param cleanupInterval 清理任务的执行间隔
         * @param timeUnit        时间单位
         * @return 当前构建器实例
         */
        public TimedDeletionMapBuilder<K, V> withCleanupInterval(long cleanupInterval, TimeUnit timeUnit) {
            this.cleanupInterval = cleanupInterval;
            this.timeUnit = timeUnit;
            return this;
        }

        /**
         * 设置当元素被清除时执行的函数。
         *
         * @param onRemoveConsumer 当元素被清除时执行的函数
         * @return 当前构建器实例
         */
        public TimedDeletionMapBuilder<K, V> withOnRemoveConsumer(Consumer<V> onRemoveConsumer) {
            this.onRemoveConsumer = onRemoveConsumer;
            return this;
        }

        /**
         * 构建并返回 TimedDeletionMap 实例。
         *
         * @return 新建的 TimedDeletionMap 实例
         * @throws IllegalStateException 如果没有指定 mapSupplier
         */
        public TimedDeletionMap<K, V> build() {
            if (mapSupplier == null) {
                throw new IllegalStateException("Map supplier must not be null");
            }
            return new TimedDeletionMap<>(mapSupplier.get(), executorService, onRemoveConsumer, cleanupInterval, timeUnit);
        }
    }

    /**
     * 安排清理任务定期运行，以移除过期的条目。
     */
    private void scheduleCleanupTask(long cleanupInterval, TimeUnit timeUnit) {
        executorService.scheduleAtFixedRate(new CleanupTask(), 0, cleanupInterval, timeUnit);
    }

    /**
     * 执行清理任务，移除所有已过期的条目。
     */
    private class CleanupTask implements Runnable {
        @Override
        public void run() {
            try {
                Collection<K> keysToRemove = new ArrayList<>();
                for (Map.Entry<K, TimedEntry<V>> entry : map.entrySet()) {
                    if (isExpired(entry.getValue())) {
                        keysToRemove.add(entry.getKey());
                        if (onRemoveConsumer != null) {
                            onRemoveConsumer.accept(entry.getValue().getValue());
                        }
                    }
                }
                keysToRemove.forEach(map::remove);
            } catch (Exception e) {
                log.error("Error during cleanup task execution: {}", e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 检查指定条目是否已过期。
     *
     * @param entry 要检查的条目
     * @return 如果条目已过期，则为 true；否则为 false
     */
    private boolean isExpired(TimedEntry<V> entry) {
        return System.currentTimeMillis() > entry.getExpirationTime();
    }

    /**
     * 存储值及其过期时间的容器类。
     *
     * @param <T> 值的类型
     */
    public static class TimedEntry<T> {
        private final T value;
        private final long expirationTime;

        /**
         * 创建一个新的 TimedEntry 实例。
         *
         * @param value          存储的值
         * @param expirationTime 值的过期时间
         */
        public TimedEntry(T value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }

        /**
         * 获取存储的值。
         */
        public T getValue() {
            return value;
        }

        /**
         * 获取值的过期时间。
         */
        public long getExpirationTime() {
            return expirationTime;
        }
    }
}



