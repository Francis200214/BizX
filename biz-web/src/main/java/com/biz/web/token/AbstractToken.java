package com.biz.web.token;


import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.JwtTokenUtils;
import com.biz.web.account.BizAccount;
import com.biz.web.account.BizAccountFactory;

import java.io.Serializable;

/**
 * @author francis
 * @create: 2023-04-18 09:23
 **/
public abstract class AbstractToken implements Token {


    private static ThreadLocal<Object> tokenObject = new ThreadLocal<>();

    private Serializable currentTokenId = null;

    @Override
    public String getCurrentToken() {
        return null;
    }

    @Override
    public BizAccount<?> getCurrentUser() {
        if (currentTokenId == null) {
            throw new RuntimeException("current token id is null");
        }
        BizAccountFactory bean = BizXBeanUtils.getBean(BizAccountFactory.class);
        return bean.getBizAccount(currentTokenId);
    }


    @Override
    public void setCurrentUser(BizAccount<?> bizAccount) {
        // 1、生成一个 token 并保存

        // 2、生成的 token 与 account 信息绑定

        // 3、返回时的 header 中存放 token

        JwtTokenUtils builder = JwtTokenUtils.JwtTokenUtilsbuilder()
//                .expire()
                .builder();
//        builder.createToken()
    }

    @Override
    public void destroy() {

    }
}
