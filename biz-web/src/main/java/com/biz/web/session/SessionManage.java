package com.biz.web.session;

import com.biz.web.account.BizAccount;

/**
 * token 会话管理
 *
 * @author francis
 * @create: 2023-04-18 10:09
 **/
public interface SessionManage {


    /**
     * 获取当前用户信息
     *
     * @param token
     * @return
     */
    Object getSession(String token);

    /**
     * 生成一个新的 Token
     *
     * @param account 用户信息
     * @return
     */
    String createSession(BizAccount<?> account);


    /**
     * 刷新 token 清除时间
     *
     * @param token
     */
    void resetSessionDiedTime(String token);


}
