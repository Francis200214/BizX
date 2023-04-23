package com.biz.web.session;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.common.utils.UUIDGenerate;
import com.biz.map.SingletonScheduledMap;
import com.biz.web.account.BizAccount;
import com.biz.web.token.TokenProperties;

/**
 * 会话管理
 *
 * @author francis
 * @create: 2023-04-18 16:33
 **/
public class AbstractSessionManage implements SessionManage {

    /**
     * 会话缓存 Map
     */
    private static SingletonScheduledMap<String, Object> sessionMap = SingletonScheduledMap.builder()
            .build();


    @Override
    public Object getSession(String token) {
        if (!sessionMap.containsKey(token)) {
            return null;
        }

        return sessionMap.get(token);
    }

    @Override
    public String createSession(BizAccount<?> account) {
        String id = UUIDGenerate.generate();
        sessionMap.put(id, Common.to(account.getId()));
        return id;
    }

    @Override
    public void resetSessionDiedTime(String token) {
        sessionMap.resetDiedCatch(token, getTokenProperties());
    }


    /**
     * 获取配置的 token 失效时间
     *
     * @return
     */
    private long getTokenProperties() {
        TokenProperties bean = BizXBeanUtils.getBean(TokenProperties.class);
        return bean.getExpire();
    }


}
