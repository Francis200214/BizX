package com.biz.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code BizXAccessLimit} 是一个自定义注解，用于限制接口的访问。
 * <p>
 * 该注解可以应用于类、构造函数或方法上，用于指定特定的访问限制规则。
 * 通过应用此注解，开发者可以控制接口的访问频率、并发数或其他限制条件，以保护系统资源和防止滥用。
 *
 * @see ElementType
 * @see RetentionPolicy
 * @see Target
 * @see Retention
 * @see Target
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BizXAccessLimit {
}
