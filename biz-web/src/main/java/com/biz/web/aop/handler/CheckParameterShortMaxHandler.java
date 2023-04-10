package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckShortMax;

import java.lang.reflect.Parameter;

/**
 * 检查 Short 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/
@BizXComponent
public class CheckParameterShortMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckShortMax.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
