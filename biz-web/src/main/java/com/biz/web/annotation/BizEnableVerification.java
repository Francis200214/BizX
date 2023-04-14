package com.biz.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 * 将注解放在类、接口、方法上都可以开启权限校验
 * 1、放在类上表示所有方法开启权限校验；
 * 2、放在接口上表示接口中所有方法开启权限校验；
 * 3、放在方法上表示该方法开启权限校验
 *
 * @author francis
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BizEnableVerification {

    /**
     * 默认为空，所有角色都能访问
     */
    String[] roles() default "*";

}
