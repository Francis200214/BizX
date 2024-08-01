package com.demo.controller;

import com.biz.common.copier.AbstractCopier;
import com.biz.common.copier.Copier;
import com.biz.common.utils.Common;
import com.biz.web.account.BizAccount;
import com.biz.web.rbac.BizAccessAllow;
import com.biz.web.rbac.BizVerification;
import com.biz.web.token.Token;
import com.demo.controller.vo.AVo;
import com.demo.controller.vo.BVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author francis
 * @since 2023-04-14 10:39
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private Token token;

    private final Copier<AVo, BVo> aVoBVoCopier = new AbstractCopier<AVo, BVo>() {
        @Override
        public BVo copy(AVo aVo, BVo bVo) {
            bVo.setId(aVo.getId());
            bVo.setName(aVo.getName());
            return bVo;
        }
    };


    @GetMapping("/login")
    public String login(@RequestParam("password") String password) {
        BizAccount<String> bizAccount = accountFactory.getBizAccount("s");
        token.setCurrentUser(bizAccount);
        UserAccount userAccount = Common.to(token.getCurrentUser());
        AVo aVo = new AVo();
        aVo.setId(userAccount.getUserId());
        aVo.setName(userAccount.getName());
        BVo copy = aVoBVoCopier.copy(aVo);
        System.out.println(copy);
        return password;
    }

    @GetMapping("/account")
    @BizVerification
    public UserAccount account() {
        UserAccount userAccount = Common.to(token.getCurrentUser());
        return userAccount;
    }


}
