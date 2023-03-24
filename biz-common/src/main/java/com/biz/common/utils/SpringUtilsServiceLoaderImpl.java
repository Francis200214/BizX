package com.biz.common.utils;


import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 对 java.util.ServiceLoader 的实现
 *
 * @author francis
 */
public class SpringUtilsServiceLoaderImpl extends AbstractServiceLoaderProvider {

    @Override
    protected <T> T load(Class<?> tClass) {

        ServiceLoader<?> loadedDrivers = ServiceLoader.load(tClass);
        final Iterator<?> iterator = loadedDrivers.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            if (tClass.getName().equals(next.getClass().getName())) {
                return Common.to(next);
            }
        }

        return null;
    }

}
