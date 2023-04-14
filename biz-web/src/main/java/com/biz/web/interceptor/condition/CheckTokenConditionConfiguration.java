package com.biz.web.interceptor.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 检查token condition
 * 当 yml 或者 properties 配置文件配置了检查token的配置则可以注入Bean
 *
 * @author francis
 * @create: 2023-04-14 15:21
 **/
public class CheckTokenConditionConfiguration implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        return env.getProperty("biz.interceptor.checkToken", Boolean.class, false);
    }

}
