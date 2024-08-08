package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXApiCheckErrorMessage;

import java.lang.annotation.*;

/**
 * 检查长度的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的长度范围。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckSize(
 *         min = 1,
 *         max = 10,
 *         error = @BizXApiCheckErrorMessage(code = 1013, message = "Length must be between 1 and 10")
 *     )
 *     private String value;
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
public @interface BizXCheckSize {

    /**
     * 指定的最小长度。
     *
     * @return 最小长度，默认为 {@link Long#MIN_VALUE}
     */
    long min() default Long.MIN_VALUE;

    /**
     * 指定的最大长度。
     *
     * @return 最大长度，默认为 {@link Long#MAX_VALUE}
     */
    long max() default Long.MAX_VALUE;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXApiCheckErrorMessage error() default @BizXApiCheckErrorMessage;

}
