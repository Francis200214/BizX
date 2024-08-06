package com.biz.rabbitmq.config;

import com.biz.common.bean.BizXBeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Configuration
public class DynamicRabbitMQConfig implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private RabbitAdmin rabbitAdmin;

    private ApplicationContext applicationContext;


    private void createQueuesAndBindings() {
        Map<String, Object> beansWithRabbitListener = applicationContext.getBeansWithAnnotation(Component.class);

        for (Object bean : beansWithRabbitListener.values()) {
            Class<?> clazz = bean.getClass();
            for (Method method : clazz.getDeclaredMethods()) {
                RabbitListener rabbitListener = AnnotationUtils.findAnnotation(method, RabbitListener.class);
                if (rabbitListener != null) {
                    for (String queueName : rabbitListener.queues()) {
                        if (StringUtils.hasText(queueName)) {
                            createQueueAndBinding(queueName);
                        }
                    }
                }
            }
        }
    }

    private void createQueueAndBinding(String queueName) {
        Queue queue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(queue);

        // 假设交换机名称与队列名称相同，用户可以根据需要自定义
        TopicExchange exchange = new TopicExchange(queueName);
        rabbitAdmin.declareExchange(exchange);

        // 假设路由键名称与队列名称相同，用户可以根据需要自定义
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueName);
        rabbitAdmin.declareBinding(binding);
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        return factory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createQueuesAndBindings();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        try {
            this.rabbitAdmin = BizXBeanUtils.getBean(RabbitAdmin.class);
        } catch (Exception e) {
            log.error("获取RabbitAdmin失败", e);
        }
    }
}
