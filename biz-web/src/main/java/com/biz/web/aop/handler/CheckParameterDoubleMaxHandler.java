package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckDoubleMax;

import java.lang.reflect.Parameter;

/**
 * 检查 Double 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-08 16:45
 **/
@BizXComponent
public class CheckParameterDoubleMaxHandler implements CheckParameterStrategy {

    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckDoubleMax.class;
    }

    @Override
    public void check(Parameter parameter, Object o) throws Exception {

    }
}
