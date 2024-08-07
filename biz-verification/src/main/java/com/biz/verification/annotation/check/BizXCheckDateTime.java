package com.biz.verification.annotation.check;

import com.biz.common.date.DateConstant;
import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查时间格式
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckDateTime {

    /**
     * 时间格式
     */
    String format() default DateConstant.DEFAULT_DATETIME;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
