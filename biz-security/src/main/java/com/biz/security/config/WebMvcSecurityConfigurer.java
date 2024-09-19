package com.biz.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Biz-Security 组件 Web 配置。
 * 当配置文件中配置了 biz.security.enabled=true 时，启用此配置。主要功能是配置拦截器，拦截器负责拦截请求，并做认证、权限控制和安全等 Web 层处理。
 *
 * @author francis
 * @create 2024-09-19
 * @since 1.0.1
 **/
@Slf4j
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class WebMvcSecurityConfigurer implements WebMvcConfigurer {


}
