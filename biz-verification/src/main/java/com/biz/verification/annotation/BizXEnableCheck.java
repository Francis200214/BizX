package com.biz.verification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打开检查入参规范
 * 在接口或者方法上加上该注解，表示打开入参规范检查
 * 规范检查
 *
 * @author francis
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BizXEnableCheck {
}
