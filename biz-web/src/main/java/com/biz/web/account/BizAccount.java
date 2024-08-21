package com.biz.web.account;

import java.io.Serializable;
import java.util.Set;

/**
 * {@code BizAccount} 接口表示当前用户的账户信息。
 * <p>
 * 该接口定义了获取用户标识、用户名称以及用户角色的方法，用于在系统中表示和管理用户的基本信息。
 * <p>
 * 通过实现此接口，开发者可以定义不同类型的用户账户，并将其用于各种业务场景中，如身份验证、授权控制等。
 *
 * @param <ID> 用户标识的类型，必须实现 {@link Serializable} 接口，以确保用户标识可以被序列化
 * @author francis
 * @version 1.0
 * @since 1.0
 */
public interface BizAccount<ID extends Serializable> {

    /**
     * 获取用户的唯一标识。
     *
     * @return 用户的唯一标识
     */
    ID getId();

    /**
     * 获取用户的名称。
     *
     * @return 用户的名称
     */
    String getName();

    /**
     * 获取用户的角色集合。
     * <p>
     * 每个角色通常代表用户在系统中的特定权限或职责。
     *
     * @return 用户的角色集合
     */
    Set<String> getRoles();
}
