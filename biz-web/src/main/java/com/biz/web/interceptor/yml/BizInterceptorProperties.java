package com.biz.web.interceptor.yml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * biz.interceptor properties 配置
 *
 * @author francis
 * @create: 2023-04-14 14:11
 **/
@Setter
@Getter
@ToString
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class BizInterceptorProperties {

    /**
     * 接口控制限流
     */
    @Value("${biz.interceptor.access:false}")
    private boolean access;

    /**
     * 接口检查token
     */
    @Value("${biz.interceptor.token:false}")
    private boolean token;

}
