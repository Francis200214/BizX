package com.biz.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Biz-Security Bean 配置管理类。
 *
 * 主要配置 Biz-Security 组件内部的 Bean。
 *
 * @author francis
 * @create 2024-09-19
 * @since 1.0.1
 **/
@Slf4j
@ConditionalOnProperty(prefix = "biz.security", name = "enabled", havingValue = "true")
public class SecurityConfigurer {


}
