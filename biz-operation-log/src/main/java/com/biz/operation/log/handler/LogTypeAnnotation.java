package com.biz.operation.log.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code LogTypeAnnotation}注解用于标注日志处理器的类型信息。
 *
 * <p>该注解应用于日志处理器类上，指定该处理器适用于特定的日志大类型和小类型。
 * 使用此注解可以将日志处理器与特定类型的日志绑定，从而在日志记录过程中动态地选择合适的处理器。</p>
 *
 * <p>注解参数包括：</p>
 * <ul>
 *     <li>{@link #largeType()}：指定日志的大类型，用于标识日志的主分类。</li>
 *     <li>{@link #smallType()}：指定日志的小类型，用于进一步细分日志的分类。</li>
 * </ul>
 *
 * <p>该注解的作用目标为类型（类、接口），并且在运行时保留。</p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * @LogTypeHandler(largeType = "SYSTEM", smallType = "ERROR")
 * public class SystemErrorLogHandler implements OperationLogHandler {
 *     // 实现日志处理逻辑
 * }
 * }</pre>
 *
 * <p>在以上示例中，{@code SystemErrorLogHandler}类被标注为处理系统错误类型的日志。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTypeAnnotation {

    /**
     * 指定日志的大类型。
     *
     * @return 日志的大类型，标识日志的主分类
     */
    String largeType();

    /**
     * 指定日志的小类型。
     *
     * @return 日志的小类型，进一步细分日志的分类
     */
    String smallType();

}
