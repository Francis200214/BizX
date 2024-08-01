package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.web.annotation.check.BizXApiCheckDoubleMin;

import java.lang.annotation.Annotation;

/**
 * 检查 Double 最小值 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:22
 **/

public class CheckParameterDoubleMinHandler implements CheckParameterStrategy {

    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckDoubleMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckDoubleMin) {
            BizXApiCheckDoubleMin check = Common.to(annotation);
            if (o instanceof Double) {
                Double num = Common.to(o);
                if (num < check.min()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
