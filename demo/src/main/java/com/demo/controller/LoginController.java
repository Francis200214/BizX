package com.demo.controller;

import com.biz.common.utils.Common;
import com.biz.oss.template.OssTemplate;
import com.biz.web.account.BizAccount;
import com.biz.web.annotation.BizXEnableApiCheck;
import com.biz.web.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author francis
 * @create: 2023-04-14 10:39
 **/
@RestController("/longin")
public class LoginController {

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private Token token;



    @GetMapping("/login")
    public void login(String password) {
        token.setCurrentUser(accountFactory.getBizAccount("11"));
        UserAccount currentUser = Common.to(token.getCurrentUser());
        System.out.println(currentUser.toString());
    }

}
