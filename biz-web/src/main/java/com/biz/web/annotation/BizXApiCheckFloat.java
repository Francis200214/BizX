package com.biz.web.annotation;

import java.lang.annotation.*;

/**
 * 检查Float类型
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckFloat {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 最小值
     */
    float min();

    /**
     * 最大值
     */
    float max();

}
