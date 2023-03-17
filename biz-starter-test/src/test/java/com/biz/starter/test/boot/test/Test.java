package com.biz.starter.test.boot.test;

import com.biz.core.bean.SpringBeanUtils;
import com.biz.starter.service.impl.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * author francis
 */
@SpringBootTest
@Slf4j
public class Test {

    public void testBeanUtils() {
        final TestServiceImpl bean = SpringBeanUtils.getBean(TestServiceImpl.class);
        log.info("bean {}", bean);
    }

}
