package com.biz.common.serviceloader;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;

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
