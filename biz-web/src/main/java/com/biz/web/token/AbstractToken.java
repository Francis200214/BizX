package com.biz.web.token;


import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.web.account.BizAccount;
import com.biz.web.account.BizAccountFactory;
import com.biz.web.session.SessionManage;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

/**
 * Token
 *
 * @author francis
 * @create: 2023-04-18 09:23
 **/
@Order(81)
public class AbstractToken implements Token, ApplicationListener<ContextRefreshedEvent> {

    private final ThreadLocal<String> token = new ThreadLocal<>();

    private final ThreadLocal<Boolean> set = ThreadLocal.withInitial(() -> false);

    private SessionManage sessionManage;

    @PostConstruct
    private void init() {
//        sessionManage = BizXBeanUtils.getBean(SessionManage.class);
    }


    @Override
    public String getCurrentToken() {
        return token.get();
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

        } catch (Exception e) {
            throw new RuntimeException("BizAccountFactory is not definition");
        }
        return null;
    }

    @Override
    public void setCurrentUser(BizAccount<?> bizAccount) {
        String session = sessionManage.createSession(bizAccount);
        token.set(session);
        set.set(true);
    }

    @Override
    public void destroy() {
        if (Common.isBlank(token.get())) {
            return;
        }
        // 销毁Session
        sessionManage.destroySession(token.get());
        token.remove();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 当所有的bean都被成功装载、初始化和刷新后，调用这里
    }

}
