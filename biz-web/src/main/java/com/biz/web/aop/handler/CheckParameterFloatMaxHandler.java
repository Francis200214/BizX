package com.biz.web.aop.handler;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckFloatMax;

import java.lang.annotation.Annotation;

/**
 * 检查 Float 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:22
 **/
@BizXComponent
public class CheckParameterFloatMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckFloatMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {

    }
}
