package com.biz.web.annotation;

import java.lang.annotation.*;

/**
 * 用于提示检查参数异常注解
 *
 * @author francis
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckError {

    /**
     * 错误码
     */
    int code() default 9999;

    /**
     * 错误信息
     */
    String message() default "";

}
