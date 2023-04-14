package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.web.annotation.check.BizXApiCheckIntegerMax;

import java.lang.annotation.Annotation;

/**
 * 检查 Integer 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:23
 **/

public class CheckParameterIntegerMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckIntegerMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckIntegerMax) {
            BizXApiCheckIntegerMax check = Common.to(annotation);
            if (o instanceof Integer) {
                Integer num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
