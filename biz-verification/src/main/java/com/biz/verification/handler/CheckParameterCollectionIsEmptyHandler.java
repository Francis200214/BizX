package com.biz.verification.handler;

import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXApiCheckCollectionIsEmpty;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 检查 Collection 是否为空 具体实现
 *
 * @author francis
 * @since 2023-04-08 16:45
 **/
@Slf4j
public class CheckParameterCollectionIsEmptyHandler implements CheckParameterStrategy {

    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
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
