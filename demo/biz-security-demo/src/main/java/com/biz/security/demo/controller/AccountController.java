package com.biz.security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户
 *
 * @author francis
 * @create 2024-09-13
 **/
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @PostMapping("/addAccount")
    public void addAccount(@RequestParam("accountName") String accountName, @RequestParam("accountPassword") String accountPassword) {
        log.info("添加账户 账号名称 {} 账号密码 {}", accountName, accountPassword);
    }

}
