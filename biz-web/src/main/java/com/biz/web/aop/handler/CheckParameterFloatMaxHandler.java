package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.web.annotation.check.BizXApiCheckFloatMax;

import java.lang.annotation.Annotation;

/**
 * 检查 Float 最大值 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:22
 **/

public class CheckParameterFloatMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckFloatMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckFloatMax) {
            BizXApiCheckFloatMax check = Common.to(annotation);
            if (o instanceof Float) {
                Float num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
