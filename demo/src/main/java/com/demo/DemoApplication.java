package com.demo;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.core.BizXEnable;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

/**
 * @author francis
 * @create: 2023-04-17 09:09
 **/
@SpringBootApplication
@BizXEnable
@EnableCaching
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
