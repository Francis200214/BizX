package com.biz.web.annotation.check;

import com.biz.common.utils.DateTimeUtils;
import com.biz.web.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查时间格式
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckDateTime {

    /**
     * 时间格式
     */
    String format() default DateTimeUtils.DEFAULT_DATETIME;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
