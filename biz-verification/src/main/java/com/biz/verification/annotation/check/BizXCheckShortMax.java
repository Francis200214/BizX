package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXCheckErrorMessage;
import com.biz.verification.error.constant.VerificationErrorConstant;

import java.lang.annotation.*;

/**
 * 检查 Short 类型最大值的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的最大值。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckShortMax(
 *         max = 100,
 *         error = @BizXCheckErrorMessage(code = 1011, message = "Value exceeds the maximum allowed")
 *     )
 *     private Short value;
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
public @interface BizXCheckShortMax {

    /**
     * 指定的最大值。
     *
     * @return 最大值，默认为 {@link Short#MAX_VALUE}
     */
    short max() default Short.MAX_VALUE;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXCheckErrorMessage error() default @BizXCheckErrorMessage(
            code = VerificationErrorConstant.CheckShortMaxError.CODE,
            message = VerificationErrorConstant.CheckShortMaxError.MESSAGE);

}
