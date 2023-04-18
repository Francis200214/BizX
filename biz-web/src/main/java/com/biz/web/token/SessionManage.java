package com.biz.web.token;

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
    BizSession getSession(String token);

    /**
     * 生成一个新的
     *
     * @param token@return
     */
    void createSession(String token);



}
