package com.biz.verification.annotation.check;

import com.biz.verification.annotation.error.BizXCheckErrorMessage;
import com.biz.verification.error.constant.VerificationErrorConstant;

import java.lang.annotation.*;

/**
 * 检查是否为 Null 的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数是否允许为 null。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckIsNull(
 *         isNull = false,
 *         error = @BizXCheckErrorMessage(code = 1008, message = "Value cannot be null")
 *     )
 *     private String value;
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
public @interface BizXCheckIsNull {

    /**
     * 指定是否可以为 null。
     *
     * @return true 表示可以为 null，false 表示不可以为 null
     */
    boolean isNull() default true;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXCheckErrorMessage error() default @BizXCheckErrorMessage(
            code = VerificationErrorConstant.CheckIsNullError.CODE,
            message = VerificationErrorConstant.CheckIsNullError.MESSAGE);

}
