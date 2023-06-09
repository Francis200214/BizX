package com.demo;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.core.BizXEnable;
import com.biz.web.interceptor.AccessLimitInterceptor;
import com.biz.web.interceptor.CheckAuthorityInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author francis
 * @create: 2023-04-17 09:09
 **/
@SpringBootApplication
@BizXEnable
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
