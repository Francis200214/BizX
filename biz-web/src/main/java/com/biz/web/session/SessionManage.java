package com.biz.web.session;

import com.biz.web.account.BizAccount;

import java.io.Serializable;
import java.util.Optional;

/**
 * token 会话管理
 *
 * @author francis
 * @create: 2023-04-18 10:09
 **/
public interface SessionManage {


    /**
     * 获取当前用户Id
     *
     * @param session
     * @return
     */
    Optional<Serializable> getSession(String session);

    /**
     * 生成一个新的会话Token
     *
     * @param account 用户信息
     * @return
     */
    String createSession(BizAccount<?> account);


    /**
     * 刷新 会话token 时间
     *
     * @param session
     */
    void resetSessionDiedTime(String session);


    /**
     * 销毁
     *
     * @param session
     */
    void destroySession(String session);

}
