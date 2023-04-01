package com.biz.core;

import com.biz.core.interceptor.CustomWebMvcConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        CustomWebMvcConfigurer.class
})
@Documented
public @interface BizX {
}
