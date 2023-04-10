package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckIntegerMin;

import java.lang.reflect.Parameter;

/**
 * 检查 Integer 最小值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:23
 **/
@BizXComponent
public class CheckParameterIntegerMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckIntegerMin.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
