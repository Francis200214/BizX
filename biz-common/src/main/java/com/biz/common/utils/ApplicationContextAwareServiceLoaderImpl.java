package com.biz.common.utils;

/**
 * ApplicationContextAware 实现
 *
 * @author francis
 */
public class ApplicationContextAwareServiceLoaderImpl extends AbstractServiceLoaderProvider {

    @Override
    protected <T> T load(Class<?> tClass) {
        return Common.to(BizXBeanUtils.getBean(tClass));
    }

}
