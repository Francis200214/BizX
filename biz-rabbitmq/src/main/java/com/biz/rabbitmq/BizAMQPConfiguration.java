package com.biz.rabbitmq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AMQP总配置类。
 * <p>
 * 该类用于配置AMQP相关的Spring组件，仅当属性 "biz.rabbitmq.enabled" 设置为 "true" 时启用。
 * 通过 @ComponentScan 注解扫描 "com.biz.rabbitmq.*" 包下的所有组件。
 * </p>
 *
 * @author francis
 * @since 2023-08-18 15:24
 **/
@Configuration
@ConditionalOnProperty(name = "biz.rabbitmq.enabled", havingValue = "true")
@ComponentScan({"com.biz.rabbitmq.*"})
public class BizAMQPConfiguration {
}
