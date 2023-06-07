package com.biz.web.token;

import com.biz.web.account.BizAccount;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * token
 *
 * @author francis
 * @create: 2023-04-18 08:47
 **/
public interface Token extends Serializable {

    /**
     * 获取当前用户的token
     *
     * @return
     */
    String getCurrentToken();

    /**
     * 获取当前用户信息
     *
     * @return
     */
    BizAccount<?> getCurrentUser();

    /**
     * 设置当前用户信息
     *
     * @param bizAccount
     */
    void setCurrentUser(BizAccount<?> bizAccount);

    /**
     * 销毁
     */
    void destroy();

    /**
     * 判断是否设置当前用户信息
     *
     * @return
     */
    boolean isSetAccount();

    /**
     * 每次调用接口初始化当前用户信息
     *
     * @param token
     */
    void initAccount(String token);

    /**
     * 设置 HttpServletResponse
     *
     * @param response 响应对象
     */
    void setHttpServletResponse(HttpServletResponse response);

}
