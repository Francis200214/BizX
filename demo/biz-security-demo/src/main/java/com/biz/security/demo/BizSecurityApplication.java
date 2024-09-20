package com.biz.security.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 测试biz-security启动类
 *
 * @author francis
 * @create 2024-09-20
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.**"})
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BizSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizSecurityApplication.class, args);
    }

}
