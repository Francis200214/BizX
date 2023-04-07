package com.biz.web.annotation;

import java.lang.annotation.*;

/**
 * 检查Integer类型
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckInteger {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 最小值
     */
    int min() default Integer.MIN_VALUE;

    /**
     * 最大值
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 默认值
     */
    int value() default 10;

}
