package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXCheckErrorMessage;
import com.biz.verification.error.constant.VerificationErrorConstant;

import java.lang.annotation.*;

/**
 * 检查 Long 类型最大值的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的最大值。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckLongMax(
 *         max = 1000L,
 *         error = @BizXCheckErrorMessage(code = 1009, message = "Value exceeds the maximum allowed")
 *     )
 *     private Long value;
 * }
 * }
 * </pre>
 *
 * @see BizXCheckErrorMessage
 * @since 2024-08-07
 * @version 1.0.0
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckLongMax {

    /**
     * 指定的最大值。
     *
     * @return 最大值，默认为 {@link Long#MAX_VALUE}
     */
    long max() default Long.MAX_VALUE;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXCheckErrorMessage error() default @BizXCheckErrorMessage(
            code = VerificationErrorConstant.CheckLongMaxError.CODE,
            message = VerificationErrorConstant.CheckLongMaxError.MESSAGE);

}
