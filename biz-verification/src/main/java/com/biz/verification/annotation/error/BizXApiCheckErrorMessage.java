package com.biz.verification.annotation.error;

import java.lang.annotation.*;

/**
 * 用于提示检查参数异常注解
 *
 * @author francis
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckErrorMessage {

    /**
     * 错误码
     */
    int code() default 99999;

    /**
     * 错误信息
     */
    String message() default "未知异常";

}
