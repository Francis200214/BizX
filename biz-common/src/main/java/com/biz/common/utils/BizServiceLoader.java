package com.biz.common.utils;


import java.util.ServiceLoader;

/**
 * 对 java.util.ServiceLoader 的实现
 *
 * @author francis
 */
public class BizServiceLoader extends AbstractServiceLoaderProvider {

    @Override
    protected <T> T load(Class<?> tClass) {
        ServiceLoader<?> loadedDrivers = ServiceLoader.load(tClass);
        for (Object next : loadedDrivers) {
            if (tClass.getName().equals(next.getClass().getName())) {
                return Common.to(next);
            }
        }
        return null;
    }

}
