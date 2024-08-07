package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查Integer类型最小值
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckIntegerMin {

    /**
     * 最小值
     */
    int min() default Integer.MIN_VALUE;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
