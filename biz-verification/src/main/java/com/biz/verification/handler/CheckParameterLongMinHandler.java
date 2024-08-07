package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckLongMin;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 检查 Long 最小值 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:24
 **/
@Slf4j
public class CheckParameterLongMinHandler implements CheckParameterStrategy {
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckLongMin.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXCheckLongMin) {
            BizXCheckLongMin check = Common.to(annotation);
            if (o instanceof Long) {
                Long num = Common.to(o);
                if (num < check.min()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
