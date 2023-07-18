package com.biz.map;


import com.biz.common.concurrent.BizScheduledFuture;
import com.biz.common.concurrent.ExecutorsUtils;
import com.biz.common.singleton.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 单例定时清除 Map
 *
 * @author francis
 */
@Slf4j
public final class SingletonScheduledMap<K, V> {

    private static final Singleton<ScheduledExecutorService> SCHEDULED_EXECUTOR_SERVICE_SINGLETON = Singleton.setSupplier(ExecutorsUtils::buildScheduledExecutorService);
    private static final AtomicLong VERSION = new AtomicLong(Long.MIN_VALUE);
    private long version = VERSION.get();
    private final Function<K, V> function;
    private final long died;
    private final Map<K, Value<V>> map;
    private final Lock lock = new ReentrantLock(true);

    public SingletonScheduledMap(Supplier<Map<K, Value<V>>> supplier, Function<K, V> function, long died) {
        this.map = supplier == null ? new ConcurrentHashMap<>() : supplier.get();
        this.function = function;
        this.died = died;
    }

    public V put(K k, V v) {
        return putCatch(k, v, died);
    }


    public V put(K k, V v, long died) {
        return putCatch(k, v, died);
    }


    public V containsKeyAndPut(K k, V v) {
        return containsKeyAndPutCatch(k, v, died);
    }


    public V containsKeyAndPut(K k, V v, long died) {
        return containsKeyAndPutCatch(k, v, died);
    }


    public V get(K k, Supplier<V> supplier) {
        return get(k, (v) -> Objects.requireNonNull(supplier, "supplier is null").get());
    }

    public V get(K k) {
        return getCache(k, () -> function);
    }

    public V get(K k, Function<K, V> function) {
        return getCache(k, () -> function);
    }


    /**
     * 判断 Map 中是否存在 key
     *
     * @param k key
     * @return true 存在 false 不存在
     */
    public boolean containsKey(K k) {
        return map.containsKey(k);
    }


    /**
     * 设置定时时间，清除 Map 中的 Key
     *
     * @param k    key
     * @param died 过期时间
     */
    public void resetDiedCatch(K k, long died) throws RuntimeException {
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
     * 删除 Map 中的 Key
     *
     * @param k
     */
    public void remove(K k) {
        lock.lock();
        try {
            if (map.containsKey(k)) {
                map.remove(k);
            }
        } finally {
            lock.unlock();
        }
    }


    /**
     * 获取缓存 Map 创建者
     *
     * @param <K>
     * @param <V>
     * @return Map 创建者
     */
    public static <K, V> SingletonMapBuilder<K, V> builder() {
        return new SingletonMapBuilder<>();
    }


    private V putCatch(K k, V v, long died) {
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


    private V containsKeyAndPutCatch(K k, V v, long died) {
        if (map.containsKey(k)) {
            synchronized (map) {
                if (map.containsKey(k)) {
                    Value<V> vValue = map.get(k);
                    vValue.scheduledFuture.cancel();
                }
            }
        }
        map.put(k, buildValue(k, v, died));
        return v;
    }


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


    private V getCache(K k, Supplier<Function<K, V>> functionSupplier) {
        long startTime = System.currentTimeMillis();
        if (version != VERSION.get()) {
            synchronized (map) {
                if (version != VERSION.get()) {
                    clear();
                    version = VERSION.get();
                }
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


    private void clear() {
        synchronized (map) {
            if (map.size() > 0) {
                map.clear();
            }
        }
    }


    /**
     * Map 的 Value 值
     * 内部封装了 ScheduledFuture，用于定时删除 Map 中的 key
     *
     * @param <V>
     */
    private static class Value<V> {
        private BizScheduledFuture scheduledFuture;
        private V v;
    }


    /**
     * 内置缓存 Map 创建者
     *
     * @param <K>
     * @param <V>
     */
    public static class SingletonMapBuilder<K, V> {
        private Supplier<Map<K, Value<V>>> supplier;
        private Function<K, V> function;
        // 默认十分钟
        private long died = 1000 * 60 * 10L;

        public SingletonMapBuilder() {
        }

        public SingletonMapBuilder<K, V> function(Function<K, V> function) {
            this.function = function;
            return this;
        }

        public SingletonMapBuilder<K, V> map(Supplier<Map<K, Value<V>>> supplier) {
            this.supplier = supplier;
            return this;
        }

        public SingletonMapBuilder<K, V> died(long died) {
            this.died = died;
            return this;
        }

        public SingletonScheduledMap build() {
            return new SingletonScheduledMap(supplier, function, died);
        }
    }

}
