package com.biz.verification.annotation.error;

import com.biz.common.utils.ErrorCodeConstant;

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
    int code() default ErrorCodeConstant.DEFAULT_ERROR_CODE;

    /**
     * 错误信息
     */
    String message() default "未知异常";

}
