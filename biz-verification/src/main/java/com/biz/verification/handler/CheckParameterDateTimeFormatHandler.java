package com.biz.verification.handler;

import com.biz.common.date.calendar.CalendarUtils;
import com.biz.common.date.datetime.DateTimeUtils;
import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckDateTimeFormat;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Calendar;

/**
 * 检查时间格式的具体实现类。
 * <p>实现了 {@link CheckParameterStrategy} 接口，提供了对 {@link BizXCheckDateTimeFormat} 注解的处理逻辑。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckDateTime(
 *         format = "yyyy-MM-dd",
 *         error = @BizXCheckErrorMessage(code = 1002, message = "Invalid date format")
 *     )
 *     private String date;
 * }
 * }
 * </pre>
 *
 * @author francis
 * @version 1.0.0
 * @see BizXCheckDateTimeFormat
 * @see BizXVerificationException
 * @see CheckParameterStrategy
 * @since 2023-04-17
 **/
@Slf4j
public class CheckParameterDateTimeFormatHandler implements CheckParameterStrategy {

    /**
     * 获取检查的注解类型。
     *
     * @return 需要检查的注解类型 {@link BizXCheckDateTimeFormat}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckDateTimeFormat.class;
    }

    /**
     * 检查参数是否符合注解的要求。
     *
     * @param annotation 需要检查的注解实例
     * @param value          需要检查的对象
     * @param className 类名称
     * @param methodName 方法名称
     * @param fieldName 参数名称
     * @throws BizXVerificationException 如果检查不通过，则抛出此异常
     */
    @Override
    public void check(Annotation annotation, Object value, String className, String methodName, String fieldName) throws BizXVerificationException {
        if (value == null) {
            return;
        }

        if (annotation instanceof BizXCheckDateTimeFormat) {
            BizXCheckDateTimeFormat check = Common.to(annotation);
            try {
                if (value instanceof String) {
                    String str = Common.to(value);
                    if (Common.isBlank(check.format())) {
                        DateTimeUtils.strToDate(str);
                    } else {
                        DateTimeUtils.strToDate(str, check.format());
                    }

                } else if (value instanceof java.util.Date) {
                    DateTimeUtils.dateToStr(Common.to(value), check.format());

                } else if (value instanceof Calendar) {
                    CalendarUtils.isValidFormat(Common.to(value), check.format());

                }
            } catch (Exception e) {
                throw new BizXVerificationException(check.error().code(), check.error().message(), className, methodName, fieldName);
            }

        }
    }
}
