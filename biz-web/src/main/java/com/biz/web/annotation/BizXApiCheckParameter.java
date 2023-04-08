package com.biz.web.annotation;


import java.lang.annotation.*;

/**
 * 检查入参是否符合规则
 *
 * @author francis
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXApiCheckParameter {
}
