package com.biz.web.token;

import com.biz.web.account.BizAccount;

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

}
