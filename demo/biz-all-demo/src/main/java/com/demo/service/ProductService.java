package com.demo.service;

import com.biz.common.concurrent.ExecutorsUtils;
import com.biz.common.random.RandomUtils;
import com.demo.service.bo.AddUserBo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private IUserService iUserService;

    private static final ExecutorService executorService = ExecutorsUtils.buildScheduledExecutorService();

    public void addProduct(Product product) {
        log.info("[ProductService] 添加产品信息: {}", product);
        executorService.execute(() -> {
            log.info("[子线程] [ProductService] 添加产品信息: {}", product);
        });
//        iUserService.addUser(
//                AddUserBo.builder()
//                        .name(RandomUtils.generateStr())
//                        .age(RandomUtils.generateNumber(50))
//                        .school(RandomUtils.generateStr())
//                        .build()
//        );
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Product {
        private String name;
        private String category;
        private int stock;
    }
}

