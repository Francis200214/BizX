package com.biz.web.token;

import java.io.Serializable;

/**
 * token
 *
 * @author francis
 * @create: 2023-04-18 08:47
 **/
public interface Token extends Serializable {

    /**
     * 获取当前用户信息
     *
     * @return
     */
    BizAccount<?> getCurrentUser();

    /**
     * 销毁
     */
    void destroy();

}
