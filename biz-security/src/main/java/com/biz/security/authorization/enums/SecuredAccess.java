package com.biz.security.authorization.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问校验
 *
 * @author francis
 * @create 2024-10-11
 * @since 1.0.1
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredAccess {

    /**
     * 需要的角色
     */
    String[] hasRole() default {};

    /**
     * 需要的权限
     */
    String[] hasAuthority() default {};

    /**
     * 是否需要登录
     * <p>
     * 指定方法或类是否必须在用户认证（登录）后才可访问。
     * 默认值为 true。
     * </p>
     */
    boolean requiresAuthentication() default true;

    /**
     * 是否需要特定的 IP 地址
     * <p>
     * 指定访问该方法或类时，允许的客户端 IP 地址或 IP 范围。
     * 可以用于加强安全控制。
     * </p>
     */
    String[] allowedIpAddresses() default {};

    /**
     * 是否需要特定的时间范围
     * <p>
     * 指定方法或类的访问时间范围，格式如 "09:00-18:00"。
     * 只能在特定时间段内访问，用于进一步控制访问时段。
     * </p>
     */
    String allowedTimeRange() default "";

    /**
     * 是否允许匿名访问
     * <p>
     * 如果为 true，则该方法或类可以匿名访问，无需登录。
     * 默认值为 false。
     * </p>
     */
    boolean allowAnonymous() default false;

}
