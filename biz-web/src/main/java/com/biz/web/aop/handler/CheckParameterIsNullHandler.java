package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckIsNull;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 检查 是否为Null 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:23
 **/
@BizXComponent
public class CheckParameterIsNullHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckIsNull.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (annotation instanceof BizXApiCheckIsNull) {
            BizXApiCheckIsNull check = Common.to(annotation);
            if (!check.isNull()) {
                if (o == null) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
