package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckLongMin;

import java.lang.annotation.Annotation;

/**
 * 检查 Long 最小值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/
@BizXComponent
public class CheckParameterLongMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckLongMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {

    }
}
