package com.biz.library.bean;

import java.lang.annotation.*;

/**
 * 加入该注解的Class，将注册到Bean中
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXComponent {
}
