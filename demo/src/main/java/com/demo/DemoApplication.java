package com.demo;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.core.BizXEnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author francis
 * @since 2023-04-17 09:09
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.**"})
@BizXEnable
@EnableCaching
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        RabbitAdmin bean = BizXBeanUtils.getBean(RabbitAdmin.class);
        log.info("bean:{}", bean);
    }

}
