package com.biz.map;


import com.biz.common.concurrent.ExecutorsUtils;
import com.biz.common.singleton.Singleton;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
public final class SingletonScheduledMap<K, V> {

    private static final Singleton<ScheduledExecutorService> SCHEDULED_EXECUTOR_SERVICE_SINGLETON = Singleton.setSupplier(ExecutorsUtils::buildScheduledExecutorService);
    private static final AtomicLong VERSION = new AtomicLong(Long.MIN_VALUE);
    private long version = VERSION.get();
    private final Function<K, V> function;
    private final long died;
    private final Map<K, V> map;
    private final Lock lock = new ReentrantLock(true);


    public SingletonScheduledMap(Supplier<Map<K, V>> supplier, Function<K, V> function, long died) {
        this.map = supplier == null ? new ConcurrentHashMap<>() : supplier.get();
        this.function = function;
        this.died = died;
    }

    public V put(K k, V v, long died) {
        lock.lock();
        try {
            if (!map.containsKey(k)) {
                SCHEDULED_EXECUTOR_SERVICE_SINGLETON.get().schedule(() -> remove(k), died, TimeUnit.MILLISECONDS);
                map.put(k, v);
            }
        } finally {
            lock.unlock();
        }
        return v;
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


    private V getCache(K k, Supplier<Function<K, V>> functionSupplier) {
        if (version != VERSION.get()) {
            synchronized (this) {
                if (version != VERSION.get()) {
                    clear();
                    version = VERSION.get();
                }
            }
        }

        if (!map.containsKey(k)) {
            return put(k, functionSupplier.get().apply(k), died);
        }
        return map.get(k);
    }


    private void clear() {
        synchronized (map) {
            if (map.size() > 0) {
                map.clear();
            }
        }
    }

    private void remove(K k) {
        if (map.containsKey(k)) {
            synchronized (map) {
                map.remove(k);
            }
        }

    }


    public static <K, V> SingletonMapBuilder<K, V> builder() {
        return new SingletonMapBuilder<>();
    }

    public static class SingletonMapBuilder<K, V> {

        private Supplier<Map<K, V>> supplier;
        private Function<K, V> function;
        private long died = 500L;

        public SingletonMapBuilder() {
        }

        public SingletonMapBuilder<K, V> function(Function<K, V> function) {
            this.function = function;
            return this;
        }

        public SingletonMapBuilder<K, V> map(Supplier<Map<K, V>> supplier) {
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
