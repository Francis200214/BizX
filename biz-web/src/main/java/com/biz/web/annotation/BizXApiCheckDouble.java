package com.biz.web.annotation;

import java.lang.annotation.*;

/**
 * 检查Double类型
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckDouble {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 最小值
     */
    double min() default Double.MIN_VALUE;

    /**
     * 最大值
     */
    double max() default Double.MAX_VALUE;

}
