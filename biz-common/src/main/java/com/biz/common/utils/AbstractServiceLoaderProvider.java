package com.biz.common.utils;

import com.biz.common.singleton.SingletonMap;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 *
 * @author francis
 */
public abstract class AbstractServiceLoaderProvider implements ServiceLoaderProvider {

    private final SingletonMap<String, Object> cache = SingletonMap.<String, Object>builder().builder();

    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public <T> T get(Class<?> tClass) {
        lock.lock();
        try {
            return Common.to(cache.get(tClass.getName(), key -> load(tClass)));

        } finally {
            lock.unlock();
        }
    }


    protected abstract <T> T load(Class<?> tClass);

}
