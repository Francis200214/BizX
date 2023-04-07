package com.biz.web.validator;

import com.biz.library.bean.BizXComponent;
import com.biz.web.annotation.BizXApiCheckString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author francis
 * @create: 2023-04-07 19:59
 **/
@BizXComponent
@Slf4j
public class CheckStringValidator implements ConstraintValidator<BizXApiCheckString, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("value {}", value);
        return true;
    }
}
