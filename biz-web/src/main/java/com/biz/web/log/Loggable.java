package com.biz.web.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author francis
 * @since 2024-05-31 15:39
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

    /**
     * 业务日志大类型
     */
    String logLargeType();

    /**
     * 业务日志小类型
     */
    String logSmallType() default "";

    /**
     * 日志内容
     * 通过 SpEL表达式获取
     */
    String content();

}
