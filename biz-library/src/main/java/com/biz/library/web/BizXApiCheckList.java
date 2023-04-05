package com.biz.library.web;

import java.lang.annotation.*;

/**
 * 检查List类型
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckList {

    /**
     * 是否可以为空
     */
    boolean isEmpty() default true;

}
