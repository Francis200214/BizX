package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckDoubleMax;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 检查 Double 最大值 具体实现
 *
 * @author francis
 * @since 2023-04-08 16:45
 **/
@Slf4j
public class CheckParameterDoubleMaxHandler implements CheckParameterStrategy {

    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckDoubleMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXCheckDoubleMax) {
            BizXCheckDoubleMax check = Common.to(annotation);
            if (o instanceof Double) {
                Double num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }


}
