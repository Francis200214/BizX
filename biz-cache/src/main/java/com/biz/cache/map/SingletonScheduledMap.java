package com.biz.cache.map;

import com.biz.common.concurrent.BizScheduledFuture;
import com.biz.common.concurrent.ExecutorsUtils;
import com.biz.common.singleton.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@code SingletonScheduledMap} 是一个线程安全的定时任务缓存Map，用于存储具有过期时间的键值对。
 * <p>
 * 该类提供了常用的 {@code put} 和 {@code get} 操作，并支持在键不存在时自动生成新值的功能。
 * 每个键值对可以设置不同的过期时间，到期后自动从缓存中移除。此类基于单例模式构建，并且可以通过
 * {@link SingletonScheduledMap.SingletonMapBuilder} 进行灵活配置和构建。
 * <p>
 * 为了确保在多线程环境中的线程安全性，缓存操作使用了 {@link ReentrantLock} 进行锁保护。
 * 内部使用了 {@link ScheduledExecutorService} 来管理和调度定时任务。
 *
 * @param <K> 缓存条目的键类型
 * @param <V> 缓存条目的值类型
 * @author francis
 * @version 1.0.2
 * @since 1.0.2
 */
@Slf4j
public final class SingletonScheduledMap<K, V> implements CacheMap<K, V> {

    /**
     * 全局的 {@link ScheduledExecutorService} 单例，用于调度定时任务。
     */
    private static final Singleton<ScheduledExecutorService> SCHEDULED_EXECUTOR_SERVICE_SINGLETON =
            Singleton.createWithSupplier(ExecutorsUtils::buildScheduledExecutorService);

    /**
     * 全局版本号，用于控制缓存的生命周期。
     */
    private static final AtomicLong VERSION = new AtomicLong(Long.MIN_VALUE);

    /**
     * 当前实例的版本号，用于跟踪缓存的有效性。
     */
    private long version = VERSION.get();

    /**
     * 当键不存在时执行的函数，用于生成新值。
     */
    private final Function<K, V> function;

    /**
     * 键值对的默认过期时间，以毫秒为单位。
     */
    private final long died;

    /**
     * 内部 {@link ConcurrentHashMap}，用于存储键值对。
     */
    private final ConcurrentHashMap<K, Value<V>> map;

    /**
     * 用于控制并发访问的锁 {@link ReentrantLock}。
     */
    private final Lock lock = new ReentrantLock(true);

    /**
     * 构造一个新的 {@code SingletonScheduledMap} 实例。
     *
     * @param supplier 提供 {@link ConcurrentHashMap} 实例的供应商
     * @param function 当键不存在时用于生成新值的函数
     * @param died     键值对的默认过期时间，以毫秒为单位
     */
    public SingletonScheduledMap(Supplier<ConcurrentHashMap<K, Value<V>>> supplier,
                                 Function<K, V> function, long died) {
        this.map = supplier == null ? new ConcurrentHashMap<>() : supplier.get();
        this.function = function;
        this.died = died;
    }

    /**
     * 将键值对存储到缓存中。如果键已存在，则直接返回原值。
     *
     * @param k 键
     * @param v 值
     * @return 存储的值
     */
    @Override
    public V put(K k, V v) {
        return putCache(k, v, died);
    }

    /**
     * 将键值对存储到缓存中，并指定过期时间。如果键已存在，则直接返回原值。
     *
     * @param k    键
     * @param v    值
     * @param died 过期时间，单位是毫秒
     * @return 存储的值
     */
    public V put(K k, V v, long died) {
        return putCache(k, v, died);
    }

    /**
     * 如果键已存在，则更新键对应的值并返回旧值；否则，添加新键值对并返回null。
     *
     * @param k 键
     * @param v 值
     * @return 旧值或null
     */
    public V containsKeyAndPut(K k, V v) {
        return containsKeyAndPutCache(k, v, died);
    }

    /**
     * 如果键已存在，则更新键对应的值并返回旧值；否则，添加新键值对并返回null，并指定过期时间。
     *
     * @param k    键
     * @param v    值
     * @param died 过期时间，单位是毫秒
     * @return 旧值或null
     */
    public V containsKeyAndPut(K k, V v, long died) {
        return containsKeyAndPutCache(k, v, died);
    }

    /**
     * 根据键获取值，如果键不存在，则使用提供的函数生成新值并存储。
     *
     * @param k        键
     * @param supplier 用于生成新值的供应商
     * @return 值
     */
    public V get(K k, Supplier<V> supplier) {
        return get(k, (v) -> Objects.requireNonNull(supplier, "supplier is null").get());
    }

    /**
     * 根据键获取值，如果键不存在，则使用默认函数生成新值并存储。
     *
     * @param k 键
     * @return 值
     */
    @Override
    public V get(K k) {
        return getCache(k, () -> function);
    }

    /**
     * 根据键获取值，如果键不存在，则使用提供的函数生成新值并存储。
     *
     * @param k        键
     * @param function 用于生成新值的函数
     * @return 值
     */
    public V get(K k, Function<K, V> function) {
        return getCache(k, () -> function);
    }

    /**
     * 检查缓存中是否存在指定的键。
     *
     * @param k 键
     * @return {@code true} 表示存在，{@code false} 表示不存在
     */
    public boolean containsKey(K k) {
        if (k == null) {
            return false;
        }
        return map.containsKey(k);
    }

    /**
     * 重置指定键的过期时间。
     *
     * @param k    键
     * @param died 新的过期时间，单位是毫秒
     * @throws RuntimeException 如果键不存在
     */
    public void resetDiedCache(K k, long died) throws RuntimeException {
        if (!map.containsKey(k)) {
            throw new RuntimeException("This key is not in the map");
        }
        lock.lock();
        try {
            if (!map.containsKey(k)) {
                throw new RuntimeException("This key is not in the map");
            }
            Value<V> vValue = map.get(k);
            // 设置延迟清除时间，重新加载
            vValue.scheduledFuture.resetDied(died);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从缓存中移除指定的键。
     *
     * @param k 键
     * @return 被移除的值
     */
    @Override
    public V remove(K k) {
        if (k == null) {
            return null;
        }

        lock.lock();
        try {
            if (!map.containsKey(k)) {
                return null;
            }
            return map.remove(k).v;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取当前缓存的大小。
     *
     * @return 缓存中的条目数量
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * 获取缓存Map的构建器。
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @return 构建器实例
     */
    public static <K, V> SingletonMapBuilder<K, V> builder() {
        return new SingletonMapBuilder<>();
    }

    /**
     * 清空缓存中的所有键值对。
     */
    private void clear() {
        if (!map.isEmpty()) {
            lock.lock();
            try {
                if (!map.isEmpty()) {
                    map.clear();
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.error("clear error", e);
                }
                throw e;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 实际存储键值对的方法。如果键不存在，则加锁确保线程安全后添加新键值对。
     *
     * @param k    键
     * @param v    值
     * @param died 过期时间，单位是毫秒
     * @return 存储的值
     */
    private V putCache(K k, V v, long died) {
        if (k == null) {
            return null;
        }

        if (!map.containsKey(k)) {
            lock.lock();
            try {
                if (!map.containsKey(k)) {
                    map.put(k, buildValue(k, v, died));
                }
            } finally {
                lock.unlock();
            }
        }
        return v;
    }

    /**
     * 如果键已存在，则取消其定时任务并更新值；否则，添加新键值对。
     *
     * @param k    键
     * @param v    值
     * @param died 过期时间，单位是毫秒
     * @return 更新后的值
     */
    private V containsKeyAndPutCache(K k, V v, long died) {
        if (map.containsKey(k)) {
            lock.lock();
            try {
                if (map.containsKey(k)) {
                    Value<V> vValue = map.get(k);
                    vValue.scheduledFuture.cancel();
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.error("containsKeyAndPutCache error", e);
                }
                throw e;
            } finally {
                lock.unlock();
            }
        }
        map.put(k, buildValue(k, v, died));
        return v;
    }

    /**
     * 构建Value对象，包含实际的值和一个定时任务，用于在过期时间到达时移除该键值对。
     *
     * @param k    键
     * @param v    值
     * @param died 过期时间，单位是毫秒
     * @return 构建的 {@code Value} 对象
     */
    private Value<V> buildValue(K k, V v, long died) {
        Value<V> value = new Value<>();
        value.v = v;
        value.scheduledFuture = BizScheduledFuture.builder()
                .runnable(() -> {
                    lock.lock();
                    try {
                        remove(k);
                    } finally {
                        lock.unlock();
                    }
                })
                .time(died)
                .scheduledExecutorService(SCHEDULED_EXECUTOR_SERVICE_SINGLETON.get())
                .build();
        value.scheduledFuture.submit();
        return value;
    }

    /**
     * 从缓存中获取值。如果键不存在或缓存已过期，则根据提供的函数生成新值并存储。
     *
     * @param k                键
     * @param functionSupplier 用于生成新值的函数
     * @return 获取的值
     */
    private V getCache(K k, Supplier<Function<K, V>> functionSupplier) {
        if (k == null) {
            return null;
        }

        if (version != VERSION.get()) {
            lock.lock();
            try {
                if (version != VERSION.get()) {
                    clear();
                    version = VERSION.get();
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.error("getCache error", e);
                }
                throw e;
            } finally {
                lock.unlock();
            }
        }

        if (!map.containsKey(k)) {
            lock.lock();
            try {
                if (!map.containsKey(k)) {
                    if (functionSupplier == null) {
                        return null;
                    }
                    return put(k, functionSupplier.get().apply(k), died);
                }
            } finally {
                lock.unlock();
            }
        }

        return map.get(k).v;
    }

    /**
     * {@code Value} 对象，包含实际的值和一个定时任务，用于在过期时间到达时移除该键值对。
     *
     * @param <V> 值的类型
     */
    private static class Value<V> {
        private BizScheduledFuture scheduledFuture;
        private V v;
    }

    /**
     * 单例映射构建器类，用于构建具有特定配置的 {@link SingletonScheduledMap} 实例。
     * 该构建器允许指定映射的生成方式、值的生成函数以及元素的过期时间。
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     */
    public static class SingletonMapBuilder<K, V> {

        /**
         * 用于提供Map实例的供应商。
         */
        private Supplier<ConcurrentHashMap<K, Value<V>>> supplier;

        /**
         * 用于根据键生成值的函数。
         */
        private Function<K, V> function;

        /**
         * 映射中元素的过期时间，以毫秒为单位。
         * 默认十分钟
         */
        private long died = 1000 * 60 * 10L;

        /**
         * 设置用于生成值的函数。
         *
         * @param function 值生成函数
         * @return 当前构建器实例，以便进行链式调用
         */
        public SingletonMapBuilder<K, V> function(Function<K, V> function) {
            this.function = function;
            return this;
        }

        /**
         * 设置用于生成Map实例的供应商。
         *
         * @param supplier Map供应商
         * @return 当前构建器实例，以便进行链式调用
         */
        public SingletonMapBuilder<K, V> map(Supplier<ConcurrentHashMap<K, Value<V>>> supplier) {
            this.supplier = supplier;
            return this;
        }

        /**
         * 设置键值对的过期时间。
         *
         * @param died 过期时间
         * @return 当前构建器实例，以便进行链式调用
         */
        public SingletonMapBuilder<K, V> died(long died) {
            this.died = died;
            return this;
        }

        /**
         * 使用当前配置构建并返回 {@link SingletonScheduledMap} 实例。
         *
         * @return 新构建的 {@code SingletonScheduledMap} 实例
         */
        public SingletonScheduledMap<K, V> build() {
            return new SingletonScheduledMap<>(supplier, function, died);
        }
    }
}
