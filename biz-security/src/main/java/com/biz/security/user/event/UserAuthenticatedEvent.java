package com.biz.security.user.event;

import com.biz.security.user.UserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户认证事件。
 * <p>
 * 当用户成功进行身份认证时发布此事件，包含认证用户的详细信息。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public class UserAuthenticatedEvent extends ApplicationEvent {

    /**
     * 认证设置的用户信息。
     */
    private final UserDetails userDetails;

    /**
     * 创建一个用户认证事件。
     *
     * @param source 事件源
     * @param userDetails 认证设置的用户信息
     */
    public UserAuthenticatedEvent(Object source, UserDetails userDetails) {
        super(source);
        this.userDetails = userDetails;
    }

    /**
     * 获取认证设置的用户信息。
     *
     * @return {@link UserDetails} 认证设置的用户信息
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

}