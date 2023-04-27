package com.demo.controller;

import com.biz.common.utils.Common;
import com.biz.web.account.BizAccount;
import com.biz.web.rbac.BizVerification;
import com.biz.web.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author francis
 * @create: 2023-04-14 10:39
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private Token token;


    @GetMapping("/login")
    public String login(@RequestParam("password") String password) {
        token.setCurrentUser(accountFactory.getBizAccount("11"));
        UserAccount currentUser = Common.to(token.getCurrentUser());
        System.out.println(currentUser.toString());
        return password;
    }

    @GetMapping("/account")
    @BizVerification
    public UserAccount account() {
        UserAccount userAccount = Common.to(token.getCurrentUser());
        return userAccount;
    }


}
