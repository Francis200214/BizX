package com.biz.verification.annotation.error;

import com.biz.common.utils.ErrorCodeConstant;
import java.lang.annotation.*;

/**
 * 用于提示检查参数异常的注解。
 * <p>该注解用于标注字段或方法参数，在参数校验失败时提供自定义的错误码和错误信息。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     // 检查参数注解
 *     @BizXCheck...(
 *         ...,
 *         error = @BizXCheckErrorMessage(code = 400, message = "参数不能为空")
 *     )
 *
 *     private String name;
 * }
 * }
 * </pre>
 *
 * @see ErrorCodeConstant
 * @since 1.0.1
 * @version 1.0.0
 * @author francis
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckErrorMessage {

    /**
     * 错误码。
     * <p>默认值为 {@link ErrorCodeConstant.DEFAULT_ERROR#CODE}。</p>
     *
     * @return 错误码
     */
    int code() default ErrorCodeConstant.DEFAULT_ERROR.CODE;

    /**
     * 错误信息。
     * <p>默认值为 {@link ErrorCodeConstant.DEFAULT_ERROR#MESSAGE}。</p>
     *
     * @return 错误信息
     */
    String message() default ErrorCodeConstant.DEFAULT_ERROR.MESSAGE;

}
