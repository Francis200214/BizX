package com.biz.core.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.biz.spring"})
public @interface BizXSpringConfiguration {
}
