package com.biz.verification.annotation.check;


import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查是否为Null
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckIsNull {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
