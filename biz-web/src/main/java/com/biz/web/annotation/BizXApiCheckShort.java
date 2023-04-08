package com.biz.web.annotation;

import java.lang.annotation.*;

/**
 * 检查Short类型
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckShort {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 默认值
     */
    short value();

    /**
     * 最小值
     */
    short min() default Short.MIN_VALUE;

    /**
     * 最大值
     */
    short max() default Short.MAX_VALUE;

}
