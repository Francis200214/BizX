package com.demo.consumer;

import com.demo.constant.SimpleQueueConstant;
import com.demo.service.bo.AddUserBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author francis
 * @since 2024-08-02 16:50
 **/
@Slf4j
@Component
public class SimpleQueueConsumer {

//    @RabbitListener(queues = SimpleQueueConstant.SIMPLE_QUEUE, autoStartup = "true")
//    public void listenSimpleQueueMessage(AddUserBo msg) throws InterruptedException {
//        log.info("simple queue message: {}", msg);
//    }
//
//    @RabbitListener(queues = SimpleQueueConstant.SIMPLE_QUEUE1, autoStartup = "true")
//    public void listenSimpleQueue1Message(AddUserBo msg) throws InterruptedException {
//        log.info("simple queue1 message: {}", msg);
//    }

}
