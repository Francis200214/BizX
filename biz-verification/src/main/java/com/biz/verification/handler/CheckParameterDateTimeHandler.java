package com.biz.verification.handler;

import com.biz.common.date.calendar.CalendarUtils;
import com.biz.common.date.datetime.DateTimeUtils;
import com.biz.common.utils.Common;
import com.biz.verification.annotation.check.BizXCheckDateTime;
import com.biz.verification.error.BizXVerificationException;
import com.biz.verification.strategy.CheckParameterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.util.Calendar;

/**
 * 检查时间格式的具体实现类。
 * <p>实现了 {@link CheckParameterStrategy} 接口，提供了对 {@link BizXCheckDateTime} 注解的处理逻辑。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckDateTime(
 *         format = "yyyy-MM-dd",
 *         error = @BizXApiCheckErrorMessage(code = 1002, message = "Invalid date format")
 *     )
 *     private String date;
 * }
 * }
 * </pre>
 *
 * @author francis
 * @version 1.0.0
 * @see BizXCheckDateTime
 * @see BizXVerificationException
 * @see CheckParameterStrategy
 * @since 2023-04-17
 **/
@Slf4j
public class CheckParameterDateTimeHandler implements CheckParameterStrategy {

    /**
     * 获取检查的注解类型。
     *
     * @return 需要检查的注解类型 {@link BizXCheckDateTime}
     */
    @Override
    public Class<? extends Annotation> getCheckAnnotation() {
        return BizXCheckDateTime.class;
    }

    /**
     * 检查参数是否符合注解的要求。
     *
     * @param annotation 需要检查的注解实例
     * @param o          需要检查的对象
     * @throws BizXVerificationException 如果检查不通过，则抛出此异常
     */
    @Override
    public void check(Annotation annotation, Object o) throws BizXVerificationException {
        if (o == null) {
            return;
        }

        if (annotation instanceof BizXCheckDateTime) {
            BizXCheckDateTime check = Common.to(annotation);
            try {
                if (o instanceof String) {
                    String str = Common.to(o);
                    if (Common.isBlank(check.format())) {
                        DateTimeUtils.strToDate(str);
                    } else {
                        DateTimeUtils.strToDate(str, check.format());
                    }

                } else if (o instanceof java.util.Date) {
                    DateTimeUtils.dateToStr(Common.to(o), check.format());

                } else if (o instanceof Calendar) {
                    CalendarUtils.isValidFormat(Common.to(o), check.format());

                }
            } catch (ParseException e) {
                throw new BizXVerificationException();

            } catch (Exception e) {
                throw new BizXVerificationException(check.error().code(), check.error().message());
            }

        }
    }
}
