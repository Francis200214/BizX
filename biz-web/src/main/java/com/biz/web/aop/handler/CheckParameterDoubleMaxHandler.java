package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckDoubleMax;

import java.lang.annotation.Annotation;

/**
 * 检查 Double 最大值 具体实现
 *
 * @author francis
 * @create: 2023-04-08 16:45
 **/
@BizXComponent
public class CheckParameterDoubleMaxHandler implements CheckParameterStrategy {

    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckDoubleMax.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckDoubleMax) {
            BizXApiCheckDoubleMax check = Common.to(annotation);
            if (o instanceof Double) {
                Double num = Common.to(o);
                if (num > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }



}
