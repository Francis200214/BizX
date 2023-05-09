package com.demo.controller;

import com.biz.web.account.BizAccount;
import com.biz.web.account.BizAccountFactory;
import org.springframework.stereotype.Component;

/**
 * @author francis
 * @create: 2023-04-26 14:28
 **/
@Component
public class AccountFactory implements BizAccountFactory<String> {

    @Override
    public BizAccount<String> getBizAccount(String s) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId("111111");

        return userAccount;
    }

}
