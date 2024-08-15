package com.biz.operation.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code OperationLog}注解用于标记需要记录操作日志的方法或类。
 *
 * <p>此注解支持在方法或类级别使用，提供对日志分类、子分类以及内容的自定义。</p>
 *
 * <p>示例用法：</p>
 * <pre>{@code
 * @OperationLog(category = "USER_OPERATION", subcategory = "LOGIN", content = "用户登录操作")
 * public void loginUser() {
 *     // 用户登录逻辑
 * }
 * }</pre>
 *
 * <p>此注解支持使用Spring Expression Language (SpEL) 来动态获取日志内容。例如：</p>
 * <pre>{@code
 * @OperationLog(category = "ORDER_OPERATION", content = "#order.id + '号订单创建'")
 * public void createOrder(Order order) {
 *     // 创建订单逻辑
 * }
 * }</pre>
 *
 * <p>以下是关于该注解的各个属性的详细说明：</p>
 *
 * <p>此注解的目标为方法和类型（类、接口等），并且其保留策略为运行时。</p>
 *
 * @see ElementType
 * @see RetentionPolicy
 * @see Target
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 获取日志分类。
     *
     * <p>该属性用于标识日志的主要分类。通常用于区分不同的业务模块或操作类别。</p>
     *
     * @return 日志的分类
     */
    String category();

    /**
     * 获取日志子分类。
     *
     * <p>该属性用于进一步细化日志的分类，默认值为空字符串。</p>
     *
     * @return 日志的子分类
     */
    String subcategory() default "";

    /**
     * 获取日志内容。
     *
     * <p>日志的具体内容。可以使用Spring Expression Language (SpEL) 表达式来动态获取。</p>
     *
     * @return 日志内容
     */
    String content();

}
