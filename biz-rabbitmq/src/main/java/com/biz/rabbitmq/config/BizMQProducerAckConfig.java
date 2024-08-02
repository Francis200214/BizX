package com.biz.rabbitmq.config;

import com.alibaba.fastjson2.JSONObject;
import com.biz.common.utils.Common;
import com.biz.rabbitmq.entity.AMQPRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息发送确认配置类。
 * <p>
 * 该类实现了{@link RabbitTemplate.ConfirmCallback}和{@link RabbitTemplate.ReturnCallback}接口，
 * 用于处理消息发送到Exchange后的确认和消息未路由到队列时的回调。
 * </p>
 * <p>
 * 消息确认机制：
 * <ul>
 *   <li>如果消息没有到达Exchange，则调用confirm回调，ack=false</li>
 *   <li>如果消息到达Exchange，则调用confirm回调，ack=true</li>
 *   <li>如果消息成功从Exchange路由到Queue，不会调用return回调</li>
 *   <li>如果消息从Exchange路由到Queue失败，则调用return回调</li>
 * </ul>
 * </p>
 *
 * @see RabbitTemplate.ConfirmCallback
 * @see RabbitTemplate.ReturnCallback
 * @see RabbitTemplate
 * @see ConnectionFactory
 * @see MessagePostProcessor
 * @see MessageProperties
 * @see RabbitAdmin
 * @see Configuration
 * @see ApplicationContextAware
 * @since 2023-08-18
 * @version 1.4.11
 * @author francis
 */
@Slf4j
@Configuration
public class BizMQProducerAckConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback, ApplicationContextAware {

    private BizAMQPConfig bizAMQPConfig;

    /**
     * 配置统一消息头部等信息的消息后处理器。
     */
    public static final MessagePostProcessor messagePostProcessor =
            new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("content_type", MessageProperties.CONTENT_TYPE_JSON);
                    return message;
                }
            };

    /**
     * 配置RabbitTemplate，设置消息转换器和回调。
     *
     * @param connectionFactory 连接工厂实例
     * @return 配置完成的RabbitTemplate实例
     */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(bizAMQPConfig.producerJackson2MessageConverter());
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

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
     * 消息确认回调方法。只保证消息到达Exchange，不保证消息可以路由到正确的Queue。
     *
     * @param correlationData 相关数据
     * @param ack 确认标志
     * @param cause 原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        String jsonData = new String(correlationData.getReturnedMessage().getBody());
        AMQPRequestEntity<?> rabbitMqRequest = JSONObject.parseObject(jsonData, AMQPRequestEntity.class);
        if (ack) {
            log.info("[MQProducerAckConfig.confirm] 消息发送成功 通道ID [{}] 时间 [{}] BODY={}", id, Common.now(), rabbitMqRequest);
        } else {
            log.error("[MQProducerAckConfig.confirm] 消息发送失败 通道ID [{}] 时间 [{}] cause={} BODY={}", id, Common.now(), cause, rabbitMqRequest);
        }
    }

    /**
     * Return消息回调方法。处理不可路由的消息。
     *
     * @param message 消息实例
     * @param replyCode 回复码
     * @param replyText 回复文本
     * @param exchange 交换器
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        AMQPRequestEntity rabbitMqRequest = JSONObject.parseObject(new String(message.getBody()), AMQPRequestEntity.class);
        log.info("[MQProducerAckConfig.returnedMessage] 消息送达MQ异常 业务ID [{}] 时间 [{}] 消息主体: {} 应答码: {} 描述: {} 交换器: {} 路由键: {}",
                rabbitMqRequest.getBusinessId(), Common.now(), rabbitMqRequest.getData(), replyCode, replyText, exchange, routingKey);
    }

    /**
     * 设置应用程序上下文，用于获取BizAMQPConfig实例。
     *
     * @param applicationContext 应用程序上下文
     * @throws BeansException 如果获取Bean失败
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        bizAMQPConfig = applicationContext.getBean(BizAMQPConfig.class);
    }

}
