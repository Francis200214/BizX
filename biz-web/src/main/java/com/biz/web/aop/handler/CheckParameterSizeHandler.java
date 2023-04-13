package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckShortMin;
import com.biz.web.annotation.check.BizXApiCheckSize;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 检查 长度 具体实现
 *
 * @author francis
 * @create: 2023-04-10 22:24
 **/
@BizXComponent
public class CheckParameterSizeHandler implements CheckParameterStrategy {
    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckSize.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckSize) {
            BizXApiCheckSize check = Common.to(annotation);
            if (o instanceof Collection) {
                Collection num = Common.to(o);
                if ((num.size() < check.min()) || num.size() > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
                return;
            }

            if (o instanceof String) {
                String num = Common.to(o);
                if ((num.length() < check.min()) || num.length() > check.max()) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }
}
