package com.biz.operation.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解。
 *
 * <p>该注解可用于方法或类级别，标记此处的操作需要被记录为日志。</p>
 * <p>用户可以通过此注解自定义日志的分类、子分类以及日志内容。</p>
 *
 * <p>示例用法：</p>
 * <pre>{@code
 * @OperationLog(category = "USER_OPERATION", subcategory = "LOGIN", content = "用户登录操作")
 * public void loginUser() {
 *     // 用户登录逻辑
 * }
 * }</pre>
 *
 * <p>该注解支持使用Spring Expression Language (SpEL) 来动态获取日志内容。</p>
 *
 * @author francis
 * @since 2024-05-31 15:39
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /**
     * 日志分类。
     *
     * <p>用于标识该日志的主要分类。通常用来区分不同的业务模块或操作类别。</p>
     *
     * @return 日志的分类
     */
    String category();

    /**
     * 日志子分类。
     *
     * <p>用于进一步细化日志的分类，默认值为空字符串。</p>
     *
     * @return 日志的子分类
     */
    String subcategory() default "";

    /**
     * 日志内容。
     *
     * <p>日志的具体内容，可以通过Spring Expression Language (SpEL)表达式来动态获取。</p>
     *
     * @return 日志内容
     */
    String content();

}
