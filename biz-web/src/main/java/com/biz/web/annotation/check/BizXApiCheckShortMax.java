package com.biz.web.annotation.check;

import com.biz.web.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查 Short 类型最大值
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckShortMax {

    /**
     * 最大值
     */
    short max() default Short.MAX_VALUE;

    /**
     * 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
