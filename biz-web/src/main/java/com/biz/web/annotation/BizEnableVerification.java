package com.biz.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code BizEnableVerification} 是一个自定义注解，用于启用权限校验。
 * <p>
 * 该注解可以应用于类、接口或方法上，以指定特定的权限校验规则：
 * <ul>
 *     <li>放在类上时，表示类中的所有方法都需要进行权限校验。</li>
 *     <li>放在接口上时，表示接口中的所有方法都需要进行权限校验。</li>
 *     <li>放在方法上时，仅该方法需要进行权限校验。</li>
 * </ul>
 * <p>
 * 通过设置 {@code roles} 属性，可以指定允许访问的角色列表。如果 {@code roles} 设置为默认值 {@code "*"}，
 * 则表示所有角色都能访问被标记的类、接口或方法。
 *
 * @author francis
 * @version 1.0
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BizEnableVerification {

    /**
     * 指定允许访问的角色列表。
     * <p>
     * 默认为 {@code "*"}，表示所有角色都能访问。
     *
     * @return 允许访问的角色数组
     */
    String[] roles() default "*";
}
