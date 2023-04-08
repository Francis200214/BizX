package com.biz.web.annotation;

import com.biz.web.validator.AbstractCheckStringValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * 检查String类型
 *
 * @author francis
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Constraint(validatedBy = {AbstractCheckStringValidator.class})
public @interface BizXApiCheckString {

    /**
     * 是否可以为null
     */
    boolean isNull() default true;

    /**
     * 默认值
     */
    String value();

}
