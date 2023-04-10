package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckIsNull;

import java.lang.reflect.Parameter;

/**
 * 检查 是否为Null 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:23
 **/
@BizXComponent
public class CheckParameterIsNullHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckIsNull.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
