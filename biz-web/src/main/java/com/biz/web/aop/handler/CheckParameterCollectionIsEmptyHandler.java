package com.biz.web.aop.handler;

import com.biz.common.utils.Common;
import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.check.BizXApiCheckCollectionIsEmpty;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 检查 Collection 是否为空 具体实现
 *
 * @author francis
 * @create: 2023-04-08 16:45
 **/
@BizXComponent
public class CheckParameterCollectionIsEmptyHandler implements CheckParameterStrategy {

    @Override
    public Class<?> getCheckAnnotation() {
        return BizXApiCheckCollectionIsEmpty.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }
        if (annotation instanceof BizXApiCheckCollectionIsEmpty) {
            BizXApiCheckCollectionIsEmpty check = Common.to(annotation);
            if (o instanceof Collection) {
                if (!check.isEmpty() && isEmpty(Common.to(o))) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }


    public static boolean isEmpty(Collection collection) {
        return Common.isEmpty(collection);
    }

}
