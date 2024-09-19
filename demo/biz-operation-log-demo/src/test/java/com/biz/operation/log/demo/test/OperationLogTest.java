package com.biz.operation.log.demo.test;

import com.biz.demo.BizOperationLogApplication;
import com.biz.demo.bo.UserInfoBo;
import com.biz.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 *
 * @author francis
 * @create 2024-09-19
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BizOperationLogApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ActiveProfiles
public class OperationLogTest {

    @Autowired
    private UserService userService;


    @Test
    public void testAddUser() {
        userService.addUser(
                UserInfoBo.builder()
                        .name("francis")
                        .age(18)
                        .school("school")
                        .build()
        );
    }

    @Test
    public void testRemoveUser() {
        userService.removeUser("1");
    }

    @Test
    public void testFindUser() {
        userService.findUser("1");
    }


}
