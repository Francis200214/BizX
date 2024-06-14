package com.demo;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.core.BizXEnable;
import com.biz.web.log.LogAspect;
import com.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author francis
 * @create: 2023-04-17 09:09
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
        LogAspect bean = BizXBeanUtils.getBean(LogAspect.class);
        log.info("bean {}", bean);
        UserService userServiceBean = BizXBeanUtils.getBean(UserService.class);
        log.info("userServiceBean {}", userServiceBean);
    }

}
