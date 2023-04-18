package com.biz.web.token;

import java.util.HashMap;
import java.util.Map;

/**
 * 会话管理
 *
 * @author francis
 * @create: 2023-04-18 16:33
 **/
public class AbstractSessionManage implements SessionManage {

    private static ThreadLocal<String> token = null;

    /**
     * 会话缓存 Map
     */
    private static Map<String, BizSession> sessionMap = new HashMap<>();


    @Override
    public BizSession getSession(String token) {
        return null;
    }

    @Override
    public void createSession(String token) {



    }





}
