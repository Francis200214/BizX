package com.demo.test;

import com.biz.common.random.RandomUtils;
import com.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 Trace （链路追踪）
 *
 * @author francis
 * @version 1.0.1
 * @create 2024-08-26
 * @since 1.0.1
 **/
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class TraceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void test1() throws InterruptedException {
        productService.addProduct(new ProductService.Product(RandomUtils.generateStr(10), RandomUtils.generateStr(10), RandomUtils.generateNumber(100)));
    }

}
