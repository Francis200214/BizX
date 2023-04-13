package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckFloatMin;

import java.lang.annotation.Annotation;

/**
 * 检查 Float 最小值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:22
 **/
@BizXComponent
public class CheckParameterFloatMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckFloatMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckFloatMin) {
            BizXApiCheckFloatMin check = Common.to(annotation);
            if (o instanceof Float) {
                Float num = Common.to(o);
                if (num < check.min()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
