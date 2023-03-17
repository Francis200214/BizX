package com.biz.starter.test.boot;

import com.biz.starter.BizXApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author francis
 */
@SpringBootApplication
public class TestBizXApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizXApplication.class, args);
    }

}
