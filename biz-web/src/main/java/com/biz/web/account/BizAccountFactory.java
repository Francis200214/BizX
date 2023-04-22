package com.biz.web.account;

import java.io.Serializable;

/**
 * 当前登录用户信息接口工厂
 *
 * @author francis
 */
public interface BizAccountFactory<ID extends Serializable> {

    BizAccount<ID> getBizAccount(ID id);

}
