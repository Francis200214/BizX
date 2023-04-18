package com.biz.web.token;


import com.biz.common.utils.BizXBeanUtils;
import com.biz.common.utils.JwtTokenUtils;

import java.io.Serializable;

/**
 *
 *
 * @author francis
 * @create: 2023-04-18 09:23
 **/
public abstract class AbstractToken implements Token {


    private static ThreadLocal<Object> tokenObject = new ThreadLocal<>();

    private Serializable currentTokenId = null;


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
        JwtTokenUtils builder = JwtTokenUtils.JwtTokenUtilsbuilder()
//                .expire()
                .builder();
//        builder.createToken()
    }

    @Override
    public void destroy() {

    }
}
