package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.web.annotation.check.BizXApiCheckLongMin;

import java.lang.annotation.Annotation;

/**
 * 检查 Long 最小值 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:24
 **/

public class CheckParameterLongMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckLongMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckLongMin) {
            BizXApiCheckLongMin check = Common.to(annotation);
            if (o instanceof Long) {
                Long num = Common.to(o);
                if (num < check.min()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
