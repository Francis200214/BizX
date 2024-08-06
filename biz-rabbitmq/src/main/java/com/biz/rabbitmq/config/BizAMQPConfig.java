package com.biz.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * AMQP 配置类，用于配置AMQP相关的Bean和消息处理方式。
 * <p>
 * 该配置类实现了{@link RabbitListenerConfigurer}接口，
 * 用于配置RabbitMQ监听器端点的消息处理工厂。
 * </p>
 *
 * @author francis
 * @version 1.4.11
 * @see RabbitListenerConfigurer
 * @see Jackson2JsonMessageConverter
 * @see MappingJackson2MessageConverter
 * @see DefaultMessageHandlerMethodFactory
 * @see MessageHandlerMethodFactory
 * @see RabbitListenerEndpointRegistrar
 * @see Configuration
 * @since 2023-08-18 13:23
 **/
@Configuration
public class BizAMQPConfig implements RabbitListenerConfigurer {


    /**
     * 配置RabbitAdmin，用于管理RabbitMQ相关信息。
     *
     * @param createConnectionFactory 连接工厂实例
     * @return 配置完成的RabbitAdmin实例
     */
    @Bean
    public RabbitAdmin rabbitAdmin(final ConnectionFactory createConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(createConnectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 创建用于将Java对象转换为JSON格式的消息转换器。
     *
     * @return Jackson2JsonMessageConverter 转换器实例
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置RabbitMQ监听器端点的消息处理工厂。
     *
     * @param registrar 用于注册监听器端点的注册器
     * @see RabbitListenerEndpointRegistrar
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    /**
     * 创建并配置消息处理器方法工厂。
     *
     * @return MessageHandlerMethodFactory 实例
     * @see DefaultMessageHandlerMethodFactory
     */
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    /**
     * 创建用于将接收到的JSON格式消息转换为Java对象的消息转换器。
     *
     * @return MappingJackson2MessageConverter 转换器实例
     * @see MappingJackson2MessageConverter
     */
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

}
