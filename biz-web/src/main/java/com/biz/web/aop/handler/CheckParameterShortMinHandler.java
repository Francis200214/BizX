package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckShortMin;

import java.lang.reflect.Parameter;

/**
 * 检查 Short 最小值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/
@BizXComponent
public class CheckParameterShortMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckShortMin.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
