package com.biz.web.session;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.singleton.Singleton;
import com.biz.common.utils.JwtToken;
import com.biz.common.utils.UUIDGenerate;
import com.biz.web.account.BizAccount;
import com.biz.web.token.TokenProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 会话管理
 *
 * @author francis
 * @create: 2023-04-18 16:33
 **/
public class AbstractSessionManage implements SessionManage {

    private static final String TOKEN_KEY = "ACCOUNT_ID";
    private static ThreadLocal<String> token = null;

    /**
     * 会话缓存 Map
     */
    private static Map<String, String> sessionMap = new HashMap<>();

    private static final Singleton<JwtToken> JWT_TOKEN_UTILS_SINGLETON = Singleton.setSupplier(AbstractSessionManage::buildJwtToken);


    @Override
    public Object getSession(String token) {
        if (!sessionMap.containsKey(token)) {
            return null;
        }
        String jwtData = sessionMap.get(token);
        JwtToken jwtToken = getJwtToken();
        if (jwtToken.checkExpire(jwtData)) {
            return jwtToken.getData(jwtData, TOKEN_KEY);

        }
        return null;
    }

    @Override
    public String createSession(BizAccount<?> account) {
        JwtToken jwtToken = getJwtToken();
        jwtToken.putData(TOKEN_KEY, account.getId());
        String id = UUIDGenerate.generate();
        sessionMap.put(id, jwtToken.createToken());
        return id;
    }


    public JwtToken getJwtToken() {
        return JWT_TOKEN_UTILS_SINGLETON.get();
    }

    private static JwtToken buildJwtToken() {
        TokenProperties tokenProperties = BizXBeanUtils.getBean(TokenProperties.class);
        return JwtToken.builder()
                .secret(tokenProperties.getSecret())
                .expire(tokenProperties.getExpire())
                .build();
    }


}
