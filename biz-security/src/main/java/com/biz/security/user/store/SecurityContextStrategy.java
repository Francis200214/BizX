package com.biz.security.user.store;

import com.biz.security.user.UserDetails;

/**
 * 当前用户信息存储策略。
 * <p>
 * 定义了用于在当前请求范围内存储、获取和清除用户认证信息的方法。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public interface SecurityContextStrategy {

    /**
     * 设置本次请求的认证用户信息。
     *
     * @param userDetails 用户信息
     */
    void setUserDetails(UserDetails userDetails);

    /**
     * 获取本次请求的用户信息。
     *
     * @return {@link UserDetails} 用户信息
     */
    UserDetails getUserDetails();

    /**
     * 清除用户信息。
     */
    void clear();

}