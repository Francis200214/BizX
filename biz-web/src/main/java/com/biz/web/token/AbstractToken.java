package com.biz.web.token;


import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.serviceloader.BizServiceLoader;
import com.biz.common.serviceloader.ServiceLoaderProvider;
import com.biz.web.account.BizAccount;
import com.biz.web.account.BizAccountFactory;
import com.biz.web.session.SessionManage;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author francis
 * @create: 2023-04-18 09:23
 **/
public abstract class AbstractToken implements Token {


    private ThreadLocal<String> token = new ThreadLocal<>();

    private final SessionManage sessionManage;

    private final Serializable currentTokenId = null;

    public AbstractToken(SessionManage sessionManage) {
        this.sessionManage = getSessionManage(sessionManage);
    }


    @Override
    public String getCurrentToken() {
        return null;
    }

    @Override
    public BizAccount<?> getCurrentUser() {
        if (token.get() == null) {
            throw new RuntimeException("current token id is null");
        }
        Optional<Serializable> session = sessionManage.getSession(token.get());
        if (!session.isPresent()) {
            return null;
        }
        try {
            BizAccountFactory bean = BizXBeanUtils.getBean(BizAccountFactory.class);
            bean.getBizAccount(session.get());

        } catch (Exception e){
            throw new RuntimeException("BizAccountFactory is not definition");
        }
        return null;
    }


    @Override
    public void setCurrentUser(BizAccount<?> bizAccount) {
        // 1、生成一个 token 并保存

        // 2、生成的 token 与 account 信息绑定

        // 3、返回时的 header 中存放 token

//        builder.createToken()
    }

    @Override
    public void destroy() {

    }


    private SessionManage getSessionManage(SessionManage sessionManage) {
        return Optional.ofNullable(sessionManage).orElse(BizServiceLoader.loadService(sessionManage.getClass()));
    }

}
