package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查 Long 类型最大值
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckLongMax {

    /**
     * 最大值
     */
    long max() default Long.MAX_VALUE;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}