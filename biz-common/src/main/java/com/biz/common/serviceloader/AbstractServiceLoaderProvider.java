package com.biz.common.serviceloader;

import com.biz.common.utils.Common;
import com.biz.map.SingletonScheduledMap;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author francis
 */
public abstract class AbstractServiceLoaderProvider implements ServiceLoaderProvider {

    private final SingletonScheduledMap<String, Object> cache = SingletonScheduledMap.<String, Object>builder().build();

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
