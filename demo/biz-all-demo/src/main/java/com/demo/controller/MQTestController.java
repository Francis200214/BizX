package com.demo.controller;

import com.demo.constant.SimpleQueueConstant;
import com.demo.service.bo.AddUserBo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MQ测试
 *
 * @author francis
 * @since 1.0.1
 **/
@RestController
@RequestMapping("/mq/test")
public class MQTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/push")
    public void push() {
        rabbitTemplate.convertAndSend(SimpleQueueConstant.SIMPLE_QUEUE,
                AddUserBo.builder()
                        .age(18)
                        .name("张三")
                        .school("北京大学")
                        .build());
    }

}
