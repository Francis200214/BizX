package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckShortMin;

import java.lang.annotation.Annotation;

/**
 * 检查 Short 最小值 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/
@BizXComponent
public class CheckParameterShortMinHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckShortMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckShortMin) {
            BizXApiCheckShortMin check = Common.to(annotation);
            if (o instanceof Short) {
                Short num = Common.to(o);
                if (num < check.min()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
