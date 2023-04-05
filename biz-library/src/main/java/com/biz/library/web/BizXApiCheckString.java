package com.biz.library.web;

import java.lang.annotation.*;

/**
 * 检查String类型
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckString {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 默认值
     */
    String value() default "";

}
