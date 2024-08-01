package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.web.annotation.check.BizXApiCheckLongMax;

import java.lang.annotation.Annotation;

/**
 * 检查 Long 最大值 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:24
 **/

public class CheckParameterLongMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckLongMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckLongMax) {
            BizXApiCheckLongMax check = Common.to(annotation);
            if (o instanceof Long) {
                Long num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
