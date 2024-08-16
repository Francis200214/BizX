package com.biz.web.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Token 配置实体
 *
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class TokenProperties {

    /**
     * Token 有效时间
     * 默认一小时
     */
    @Value("${biz.token.expire:3600000}")
    private Long expire;

}
