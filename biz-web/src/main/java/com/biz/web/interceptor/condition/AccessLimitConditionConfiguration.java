package com.biz.web.interceptor.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 接口限流 condition
 * 当 yml 或者 properties 配置文件配置了接口限流的配置则可以注入Bean
 *
 * @author francis
 * @create: 2023-04-14 15:21
 **/
public class AccessLimitConditionConfiguration implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        return env.getProperty("biz.interceptor.access", Boolean.class, false);
    }

}
