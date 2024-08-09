package com.biz.verification.annotation.check;

import com.biz.common.date.DateConstant;
import com.biz.verification.annotation.error.BizXCheckErrorMessage;
import com.biz.verification.error.constant.VerificationErrorConstant;

import java.lang.annotation.*;

/**
 * 检查时间格式的注解。
 * <p>用于标注字段或方法参数，指定该字段或参数的时间格式是否符合要求。</p>
 * <p>目前支持字符串类型的时间、java.util.Date和Calendar</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckDateTime(
 *         format = "yyyy-MM-dd HH:mm:ss",
 *         error = @BizXCheckErrorMessage(code = 1001, message = "Invalid date format")
 *     )
 *     private String dateTime;
 * }
 * }
 * </pre>
 *
 * @see DateConstant
 * @see BizXCheckErrorMessage
 * @since 2024-08-07
 * @version 1.0.0
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckDateTimeFormat {

    /**
     * 时间格式。
     * <p>默认值为 {@link DateConstant#DEFAULT_DATETIME}。</p>
     *
     * @return 时间格式字符串
     */
    String format() default DateConstant.DEFAULT_DATETIME;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXCheckErrorMessage error() default @BizXCheckErrorMessage(
            code = VerificationErrorConstant.CheckDateTimeFormatError.CODE,
            message = VerificationErrorConstant.CheckDateTimeFormatError.MESSAGE);

}
