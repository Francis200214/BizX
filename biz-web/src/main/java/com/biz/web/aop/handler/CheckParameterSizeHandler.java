package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;

import java.lang.reflect.Parameter;

/**
 * 检查 长度 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/
@BizXComponent
public class CheckParameterSizeHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return null;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
