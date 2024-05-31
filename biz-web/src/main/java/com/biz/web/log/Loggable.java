package com.biz.web.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author francis
 * @create 2024-05-31 15:39
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {

    /**
     * 日志类型
     */
    String logType();

    /**
     * 操作人
     * 通过 SpEL表达式获取
     */
    String operatorId() default "";


    /**
     * 操作人
     * 通过 SpEL表达式获取
     */
    String operatorName() default "";

    /**
     * 日志内容
     * 通过 SpEL表达式获取
     */
    String content();

}
