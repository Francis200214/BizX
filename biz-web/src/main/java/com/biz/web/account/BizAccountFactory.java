package com.biz.web.account;

import java.io.Serializable;

/**
 * {@code BizAccountFactory} 接口用于创建当前登录用户信息的工厂接口。
 * <p>
 * 该接口定义了一个方法 {@link #getBizAccount(Serializable)}，用于根据用户标识创建或获取 {@link BizAccount} 实例。
 * 通过实现此接口，开发者可以根据不同的用户标识生成相应的用户账户信息，从而在系统中灵活管理和使用用户数据。
 *
 * @param <ID> 用户标识的类型，必须实现 {@link Serializable} 接口，以确保用户标识可以被序列化
 * @author francis
 * @version 1.0
 * @since 1.0
 * @see BizAccount
 */
public interface BizAccountFactory<ID extends Serializable> {

    /**
     * 根据用户标识获取对应的 {@link BizAccount} 实例。
     * <p>
     * 该方法用于通过用户标识创建或检索当前登录用户的账户信息。
     *
     * @param id 用户的唯一标识
     * @return 对应的 {@link BizAccount} 实例
     */
    BizAccount<ID> getBizAccount(ID id);

}
