package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckFloatMax;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 检查 Float 最大值 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:22
 **/
@Slf4j
public class CheckParameterFloatMaxHandler implements CheckParameterStrategy {
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckFloatMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXCheckFloatMax) {
            BizXCheckFloatMax check = Common.to(annotation);
            if (o instanceof Float) {
                Float num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}