package com.biz.cache.map;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code ThreadSafeLRUCache} 是一个线程安全的 LRU（最近最少使用）缓存实现，
 * 基于 {@link LinkedHashMap} 和 {@link ReentrantLock} 实现。
 *
 * <p>该缓存使用 {@link LinkedHashMap} 来维护缓存条目的访问顺序，并通过重写 {@code removeEldestEntry} 方法
 * 实现 LRU 淘汰策略。当缓存中的条目数量超过 {@code maxSize} 时，最老的条目（即最久未被访问的条目）将被自动移除。</p>
 *
 * <p>为了确保在多线程环境中的线程安全性，缓存的所有操作都被 {@link ReentrantLock} 锁保护。
 * 这保证了并发访问时的缓存一致性，但也意味着在高并发情况下可能会存在一定的锁竞争。</p>
 *
 * <h3>示例用法:</h3>
 * <pre>{@code
 * ThreadSafeLRUCache<String, String> cache = new ThreadSafeLRUCache<>(100);
 * cache.put("key1", "value1");
 * String value = cache.get("key1");
 * cache.remove("key1");
 * int size = cache.size();
 * }</pre>
 *
 * @param <K> 缓存条目的键类型
 * @param <V> 缓存条目的值类型
 * @author francis
 * @version 1.0.2
 * @since 1.0.2
 * @see LinkedHashMap
 * @see ReentrantLock
 */
public class ThreadSafeLRUCache<K, V> implements CacheMap<K, V> {

    /**
     * 缓存的最大容量。当缓存中的条目数超过此值时，最老的条目将会被自动移除。
     */
    private final int maxSize;

    /**
     * 用于存储缓存条目的 {@link LinkedHashMap}。该映射按访问顺序维护条目，
     * 并通过重写的 {@code removeEldestEntry} 方法实现 LRU 淘汰策略。
     */
    private final Map<K, V> cacheMap;

    /**
     * 用于控制并发访问的锁 {@link ReentrantLock}。确保在多线程环境下，
     * 对缓存条目的访问和修改是安全的。
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 构造一个具有指定最大容量的线程安全 LRU 缓存。
     *
     * @param maxSize 缓存的最大容量，当缓存中的条目数超过此值时，最老的条目将会被移除。
     */
    public ThreadSafeLRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.cacheMap = new LinkedHashMap<K, V>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > ThreadSafeLRUCache.this.maxSize;
            }
        };
    }

    /**
     * 向缓存中添加一个新的键值对。
     *
     * @param key   缓存的键，不可为 {@code null}
     * @param value 缓存的值，不可为 {@code null}
     * @return 返回先前与此键关联的值，如果没有旧值则返回 {@code null}
     */
    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            return cacheMap.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从缓存中获取一个值。
     *
     * @param key 要获取的键，不可为 {@code null}
     * @return 返回与此键关联的值，如果键不存在或已被移除，则返回 {@code null}
     */
    @Override
    public V get(K key) {
        lock.lock();
        try {
            return cacheMap.get(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从缓存中移除一个键值对。
     *
     * @param key 要移除的键，不可为 {@code null}
     * @return 返回与此键关联的值，如果键不存在或已被移除，则返回 {@code null}
     */
    @Override
    public V remove(K key) {
        lock.lock();
        try {
            return cacheMap.remove(key);
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
        lock.lock();
        try {
            return cacheMap.size();
        } finally {
            lock.unlock();
        }
    }
}
