package com.biz.rabbitmq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ总配置类
 *
 * @author francis
 * @create: 2023-08-18 15:24
 **/
@Configuration
@ConditionalOnProperty(prefix = "biz.rabbitmq")
@ComponentScan({"com.biz.rabbitmq.*"})
public class BizRabbitMQConfiguration {
}
