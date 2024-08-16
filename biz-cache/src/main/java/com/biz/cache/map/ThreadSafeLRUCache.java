package com.biz.cache.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code ThreadSafeLRUCache} 是一个线程安全的LRU（最近最少使用）缓存实现。
 *
 * <p>该缓存采用 {@link ConcurrentHashMap} 来存储缓存条目，并使用 {@link LinkedBlockingDeque}
 * 维护键的访问顺序，从而实现LRU淘汰策略。通过这种设计，可以在多线程环境中安全且高效地
 * 执行缓存操作。</p>
 *
 * <p>当缓存的条目数量超过指定的最大容量时，最老的条目（即最久未被访问的条目）将会被自动移除，
 * 以确保缓存始终维持在预设的容量范围内。</p>
 *
 * <p>该类设计用于在高并发环境下工作，通过使用 {@link ReentrantLock} 确保在多线程操作下，
 * 缓存的访问和修改保持一致性。</p>
 *
 * @param <K> 缓存条目的键类型
 * @param <V> 缓存条目的值类型
 * @author francis
 * @version 1.0.2
 * @since 1.0.2
 */
public class ThreadSafeLRUCache<K, V> {

    /**
     * 缓存的最大容量，当缓存中的条目数超过此值时，
     * 最老的条目将会被移除。
     */
    private final int maxSize;

    /**
     * 用于存储缓存条目的 {@link ConcurrentHashMap}，其线程安全的特性
     * 能够在多线程环境下提供高效的读写操作。
     */
    private final ConcurrentHashMap<K, V> cacheMap;

    /**
     * 用于维护缓存条目访问顺序的双端队列 {@link LinkedBlockingDeque}，
     * 队列头部的元素为最老的条目，队列尾部的元素为最新的条目。
     */
    private final LinkedBlockingDeque<K> orderDeque;

    /**
     * 用于控制并发访问的锁 {@link ReentrantLock}，确保在多线程操作时
     * 对缓存条目和顺序队列的访问是安全的。
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 构造一个具有指定最大容量的线程安全LRU缓存。
     *
     * @param maxSize 缓存的最大容量，当缓存中的条目数超过此值时，最老的条目将会被移除
     */
    public ThreadSafeLRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.cacheMap = new ConcurrentHashMap<>(maxSize);
        this.orderDeque = new LinkedBlockingDeque<>();
    }

    /**
     * 向缓存中添加一个新的键值对。
     *
     * <p>如果缓存已满（即条目数量达到或超过最大容量），
     * 则移除最老的条目（队列头部的条目），然后将新条目添加到队列的尾部和缓存中。</p>
     *
     * @param key 缓存的键，不可为 {@code null}
     * @param value 缓存的值，不可为 {@code null}
     */
    public void put(K key, V value) {
        lock.lock();
        try {
            if (cacheMap.containsKey(key)) {
                // 如果键已存在，先从队列中移除旧的位置
                orderDeque.remove(key);
            } else if (cacheMap.size() >= maxSize) {
                // 如果缓存已满，移除最老的条目
                K oldestKey = orderDeque.pollFirst();
                if (oldestKey != null) {
                    cacheMap.remove(oldestKey);
                }
            }
            // 将新条目添加到队列尾部和缓存中
            orderDeque.offerLast(key);
            cacheMap.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从缓存中获取一个值。
     *
     * <p>如果键存在，则返回对应的值，并将该键移到队列的尾部，以表示最近访问过。
     * 如果键不存在，则返回 {@code null}。</p>
     *
     * @param key 要获取的键，不可为 {@code null}
     * @return 如果存在，返回对应的值，否则返回 {@code null}
     */
    public V get(K key) {
        lock.lock();
        try {
            if (!cacheMap.containsKey(key)) {
                return null;
            }
            // 更新队列中的顺序
            orderDeque.remove(key);
            orderDeque.offerLast(key);
            return cacheMap.get(key);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取当前缓存的大小。
     *
     * @return 缓存中的条目数量
     */
    public int size() {
        return cacheMap.size();
    }
}
