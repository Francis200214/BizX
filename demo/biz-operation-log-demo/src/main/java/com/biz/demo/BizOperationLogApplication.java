package com.biz.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 操作日志测试Demo启动类
 *
 * @author francis
 * @version 1.0.1
 * @create 2024-09-19
 * @since 1.0.1
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.**"})
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BizOperationLogApplication {


    public static void main(String[] args) {
        SpringApplication.run(BizOperationLogApplication.class, args);
    }

}
