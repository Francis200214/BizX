package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查 Double 类型最小值的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的最小值。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckDoubleMin(
 *         min = 0.1,
 *         error = @BizXApiCheckErrorMessage(code = 1003, message = "Value is below the minimum allowed")
 *     )
 *     private Double value;
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
public @interface BizXCheckDoubleMin {

    /**
     * 指定的最小值。
     *
     * @return 最小值，默认为 {@link Double#MIN_VALUE}
     */
    double min() default Double.MIN_VALUE;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
