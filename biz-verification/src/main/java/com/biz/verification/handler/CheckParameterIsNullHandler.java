package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXApiCheckIsNull;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 检查 是否为Null 具体实现
 *
 * @author francis
 * @since 2023-04-10 22:23
 **/
@Slf4j
public class CheckParameterIsNullHandler implements CheckParameterStrategy {
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXApiCheckIsNull.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (annotation instanceof BizXApiCheckIsNull) {
            BizXApiCheckIsNull check = Common.to(annotation);
            if (!check.isNull() && isNull(o)) {
                throw new RuntimeException(check.error().message());
            }
        }
    }


    public static boolean isNull(Object o) {
        return o == null ? true : false;
    }

}
