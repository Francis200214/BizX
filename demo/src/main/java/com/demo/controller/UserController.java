package com.demo.controller;

import com.demo.service.UserService;
import com.demo.service.bo.AddUserBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.controller.UserController.USER_CACHE;

/**
 * @author francis
 * @create: 2023-08-19 15:57
 **/
@RestController
@RequestMapping("/user")
@Slf4j
@CacheConfig(cacheNames = USER_CACHE)
public class UserController {

    public static final String USER_CACHE = "userCache";

    @Autowired
    private UserService userService;

//    @CacheEvict
    @GetMapping("/addUser")
    public String addUser() {
        AddUserBo addUserBo = new AddUserBo();
        addUserBo.setName("张三");
        addUserBo.setAge(18);
        addUserBo.setSchool("清华");

        userService.addUser(addUserBo);

        userService.deleteUser("1");
        return "dssd";
    }


//    @Cacheable
    @GetMapping("/findUser")
    public String findUser() {
        log.info("查询...");
        return "dssd";
    }

}
