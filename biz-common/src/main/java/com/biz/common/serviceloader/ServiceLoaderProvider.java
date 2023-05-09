package com.biz.common.serviceloader;


public interface ServiceLoaderProvider {

    /**
     * 加载Bean
     *
     * @param tClass
     * @return
     */
    <T> T get(Class<?> tClass);

}
