package com.biz.verification.handler;

import com.biz.common.date.datetime.DateTimeUtils;
import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXApiCheckDateTime;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 检查时间格式 具体实现
 *
 * @author francis
 * @since 2023-04-17 14:16
 **/
@Slf4j
public class CheckParameterDateTimeHandler implements CheckParameterStrategy {

    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXApiCheckDateTime.class;
    }

    @Override
    public void check(Annotation annotation, Object o) throws Exception {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXApiCheckDateTime) {
            BizXApiCheckDateTime check = Common.to(annotation);
            if (o instanceof String) {
                String str = Common.to(o);
                try {
                    if (Common.isBlank(check.format())) {
                        DateTimeUtils.strToDate(str);
                    } else {
                        DateTimeUtils.strToDate(str, check.format());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(check.error().message());
                }
            }
        }
    }


}
