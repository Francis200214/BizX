package com.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.biz.common.bean.BizXBeanUtils;
import com.biz.core.BizXEnable;
import com.biz.oss.properties.OssProperties;
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
//        OssProperties bean = BizXBeanUtils.getBean(OssProperties.class);
//        System.out.println(bean);
//        AmazonS3 amazonS3 = BizXBeanUtils.getBean(AmazonS3.class);
//        System.out.println(amazonS3);
    }

}
