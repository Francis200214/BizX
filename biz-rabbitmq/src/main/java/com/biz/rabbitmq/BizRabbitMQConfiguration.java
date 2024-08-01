package com.biz.rabbitmq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ总配置类
 *
 * @author francis
 * @since 2023-08-18 15:24
 **/
@Configuration
@ConditionalOnProperty(name = "biz.rabbitmq.enabled", havingValue = "true")
@ComponentScan({"com.biz.rabbitmq.*"})
public class BizRabbitMQConfiguration {
}
