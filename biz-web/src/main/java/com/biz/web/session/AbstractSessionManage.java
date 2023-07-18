package com.biz.web.session;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.common.id.UUIDGenerate;
import com.biz.map.SingletonScheduledMap;
import com.biz.web.account.BizAccount;
import com.biz.web.token.TokenProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.Optional;

/**
 * 会话管理
 *
 * @author francis
 * @create: 2023-04-18 16:33
 **/
@Slf4j
@Order(80)
public class AbstractSessionManage implements SessionManage, InitializingBean {

    /**
     * 会话缓存 Map
     */
    private static final SingletonScheduledMap<String, Serializable> SESSION_MAP = SingletonScheduledMap.builder()
//            .died(getDiedTime())
            .build();


    @Override
    public Optional<Serializable> getSession(String token) {
        if (!SESSION_MAP.containsKey(token)) {
            return Optional.empty();
        }

        return Optional.of(SESSION_MAP.get(token));
    }

    @Override
    public String createSession(BizAccount<?> account) {
        String id = UUIDGenerate.generate();
        SESSION_MAP.containsKeyAndPut(id, Common.to(account.getId()));
        return id;
    }

    @Override
    public void resetSessionDiedTime(String token) {
        try {
            SESSION_MAP.resetDiedCatch(token, getDiedTime());
        } catch (RuntimeException e) {
            log.error("reset key appear error in map ", e);
        }
    }

    @Override
    public void destroySession(String session) {
        SESSION_MAP.remove(session);
    }


    /**
     * 获取配置的 token 失效时间
     *
     * @return
     */
    private static long getDiedTime() {
        TokenProperties bean = BizXBeanUtils.getBean(TokenProperties.class);
        return bean.getExpire();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        SESSION_MAP.resetDiedCatch();
//        BizXBeanUtils.getBean(TokenProperties.class);
    }
}
