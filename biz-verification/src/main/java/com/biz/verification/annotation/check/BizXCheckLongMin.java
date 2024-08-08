package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查 Long 类型最小值的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的最小值。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckLongMin(
 *         min = 0L,
 *         error = @BizXApiCheckErrorMessage(code = 1010, message = "Value is below the minimum allowed")
 *     )
 *     private Long value;
 * }
 * }
 * </pre>
 *
 * @see BizXApiCheckErrorMessage
 * @since 2024-08-07
 * @version 1.0.0
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckLongMin {

    /**
     * 指定的最小值。
     *
     * @return 最小值，默认为 {@link Long#MIN_VALUE}
     */
    long min() default Long.MIN_VALUE;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
