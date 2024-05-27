package com.biz.rabbitmq.config;

import cn.hutool.json.JSONUtil;
import com.biz.common.utils.Common;
import com.biz.rabbitmq.entity.RabbitMqRequestEntity;
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
 * 消息发送确认
 * ConfirmCallback  只确认消息是否正确到达 Exchange 中
 * ReturnCallback   消息没有正确到达队列时触发回调，如果正确到达队列不执行
 * 1. 如果消息没有到exchange,则confirm回调,ack=false
 * 2. 如果消息到达exchange,则confirm回调,ack=true
 * 3. exchange到queue成功,则不回调return
 * 4. exchange到queue失败,则回调return
 *
 * @author francis
 * @create: 2023-08-18 15:00
 **/
@Slf4j
@Configuration
public class BizMQProducerAckConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback, ApplicationContextAware {


    private BizRabbitConfig bizRabbitConfig;

    /**
     * 配置统一head...等
     *
     * @param
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
     * 设置请求body数据结构为JSON
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(bizRabbitConfig.producerJackson2MessageConverter());
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    /**
     * 配置admin管理MQ相关信息
     *
     * @param createConnectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(final ConnectionFactory createConnectionFactory) {
        return new RabbitAdmin(createConnectionFactory);
    }

    /**
     * confirm机制只保证消息到达exchange，不保证消息可以路由到正确的queue,如果exchange错误，就会触发confirm机制
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 消息发送时间
        String id = correlationData.getId();
        String jsonData = new String(correlationData.getReturnedMessage().getBody());
        RabbitMqRequestEntity rabbitMqRequest = JSONUtil.toBean(JSONUtil.toJsonStr(jsonData), RabbitMqRequestEntity.class);
        if (ack) {
            log.info("[MQProducerAckConfig.confirm] 消息发送成功通道id [{}] 时间 [{}]  BODY={}", id, Common.now(), rabbitMqRequest);
        } else {
            log.error("[MQProducerAckConfig.confirm] 消息发送失败通道id [{}] 时间 [{}] cause={}  BODY={}", id, Common.now(), cause, rabbitMqRequest);
        }
    }

    /**
     * Return 消息机制用于处理一个不可路由的消息。在某些情况下，如果我们在发送消息的时候，当前的 exchange 不存在或者指定路由 key 路由不到，这个时候我们需要监听这种不可达的消息
     * 就需要这种return机制
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        RabbitMqRequestEntity rabbitMqRequest = JSONUtil.toBean(JSONUtil.toJsonStr(new String(message.getBody())), RabbitMqRequestEntity.class);
        // 反序列化对象输出
        log.info("[MQProducerAckConfig.returnedMessage]消息送达MQ异常_业务id[{}]时间[{}]  \n 消息主体: {} \n 应答码: {} \n 描述: {} \n 消息使用的交换器: {} \n 消息使用的路由键: {}"
                , rabbitMqRequest.getBusinessId()
                , Common.now()
                , rabbitMqRequest.getData()
                , replyCode
                , replyText
                , exchange
                , routingKey);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        bizRabbitConfig = applicationContext.getBean(BizRabbitConfig.class);
    }


}
