package com.biz.web.account;

import java.io.Serializable;
import java.util.Set;

/**
 * 当前的用户信息
 *
 * @author francis
 */
public interface BizAccount<ID extends Serializable> {

    /**
     * 用户Id
     */
    ID getId();

    /**
     * 用户名称
     */
    String getName();

    /**
     * 用户的角色
     */
    Set<String> getRoles();

}
