package com.biz.web.annotation;

import java.lang.annotation.*;

/**
 * 检查Long类型
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckLong {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 默认值
     */
    long value();


}
