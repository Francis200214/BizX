package com.biz.library.web;

import java.lang.annotation.*;

/**
 * web 接口日志打印数据注解
 *
 * @author francis
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizLog {

    /**
     * 日志级别
     */
    String logLevel() default "info";

}
