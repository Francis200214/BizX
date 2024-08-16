package com.demo.test;

import com.biz.common.random.RandomUtils;
import com.demo.service.IUserService;
import com.demo.service.bo.AddUserBo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class OperationLogTestService {


    @Autowired
    private IUserService IUserService;

    @Test
    public void test1() throws InterruptedException {
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 开始测试
        log.info("Starting performance test...");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            final int index = i;
            executorService.submit(() -> {
                IUserService.addUser(
                        AddUserBo.builder()
                                .name(RandomUtils.generateStr())
                                .age(RandomUtils.generateNumber(50))
                                .school(RandomUtils.generateStr())
                                .addUserBo(
                                        AddUserBo.builder()
                                                .name(RandomUtils.generateStr())
                                                .age(RandomUtils.generateNumber(50))
                                                .school(RandomUtils.generateStr())
                                                .build()
                                )
                                .build()
                );
            });
        }

        // 关闭线程池并等待任务完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        log.info("Performance test completed in {} ms", (endTime - startTime));
    }

    public static void main(String[] args) throws InterruptedException {

    }

}
