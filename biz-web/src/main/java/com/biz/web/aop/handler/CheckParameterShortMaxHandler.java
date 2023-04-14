package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.web.annotation.check.BizXApiCheckShortMax;

import java.lang.annotation.Annotation;

/**
 * 检查 Short 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/

public class CheckParameterShortMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckShortMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckShortMax) {
            BizXApiCheckShortMax check = Common.to(annotation);
            if (o instanceof Short) {
                Short num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
