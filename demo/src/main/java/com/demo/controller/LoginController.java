package com.demo.controller;

import com.biz.web.annotation.BizXEnableApiCheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author francis
 * @create: 2023-04-14 10:39
 **/
@RestController("/longin")
public class LoginController {


    @GetMapping("/login")
    @BizXEnableApiCheck
    public void login(String password) {
        System.out.println("login");
    }

}
