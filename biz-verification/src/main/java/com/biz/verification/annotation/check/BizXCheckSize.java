package com.biz.verification.annotation.check;


import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查长度
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckSize {

    /**
     * 长度最小值
     */
    long min() default Long.MIN_VALUE;

    /**
     * 长度最大值
     */
    long max() default Long.MAX_VALUE;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
