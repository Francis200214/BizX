package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckIntegerMin;

import java.lang.annotation.Annotation;

/**
 * 检查 Integer 最小值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:23
 **/
@BizXComponent
public class CheckParameterIntegerMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckIntegerMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckIntegerMin) {
            BizXApiCheckIntegerMin check = Common.to(annotation);
            if (o instanceof Integer) {
                Integer num = Common.to(o);
                if (num < check.min()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
