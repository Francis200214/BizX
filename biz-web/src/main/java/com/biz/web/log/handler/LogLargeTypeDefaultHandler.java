package com.biz.web.log.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志大类型默认处理
 *
 * @author francis
 * @create 2024-05-31 16:29
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogLargeTypeDefaultHandler {

    /**
     * 日志大类型
     */
    String value();

}
