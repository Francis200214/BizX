package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;

import java.lang.reflect.Parameter;

/**
 * 检查 String 具体实现
 *
 * @author francis
 * @create: 2023-04-08 16:45
 **/
@BizXComponent
public class CheckParameterStringHandler implements CheckParameterStrategy {

    @Override
    public Class<?> getCheckAnnotation() {
        return String.class;
    }

    @Override
    public void check(Parameter parameter, Object o) {

    }
}
