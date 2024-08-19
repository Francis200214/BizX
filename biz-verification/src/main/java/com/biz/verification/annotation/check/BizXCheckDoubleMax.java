package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXCheckErrorMessage;
import com.biz.verification.error.constant.VerificationErrorConstant;

import java.lang.annotation.*;

/**
 * 检查 Double 类型最大值的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的最大值。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckDoubleMax(
 *         max = 100.0,
 *         error = @BizXCheckErrorMessage(code = 1002, message = "Value exceeds the maximum allowed")
 *     )
 *     private Double value;
 * }
 * }
 * </pre>
 *
 * @see BizXCheckErrorMessage
 * @since 1.0.1
 * @version 1.0.0
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckDoubleMax {

    /**
     * 指定的最大值。
     *
     * @return 最大值，默认为 {@link Double#MAX_VALUE}
     */
    double max() default Double.MAX_VALUE;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXCheckErrorMessage error() default @BizXCheckErrorMessage(
            code = VerificationErrorConstant.CheckDoubleMaxError.CODE,
            message = VerificationErrorConstant.CheckDoubleMaxError.MESSAGE);

}
