package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckIntegerMax;

import java.lang.reflect.Parameter;

/**
 * 检查 Integer 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:23
 **/
@BizXComponent
public class CheckParameterIntegerMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckIntegerMax.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
