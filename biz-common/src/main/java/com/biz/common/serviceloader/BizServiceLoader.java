package com.biz.common.serviceloader;


import com.biz.common.utils.Common;

import java.util.ServiceLoader;

/**
 * 对 java.util.ServiceLoader 的实现
 *
 * @author francis
 */
public class BizServiceLoader extends AbstractServiceLoaderProvider {

    @Override
    protected <T> T load(Class<?> tClass) {
        return loadService(tClass);
    }


    public static <T> T loadService(Class<?> clazz) {
        ServiceLoader<?> loadedDrivers = ServiceLoader.load(clazz);
        for (Object next : loadedDrivers) {
            if (clazz.getName().equals(next.getClass().getName())) {
                return Common.to(next);
            }
        }
        return null;
    }

}
