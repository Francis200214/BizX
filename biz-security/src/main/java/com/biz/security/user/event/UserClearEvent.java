package com.biz.security.user.event;

import com.biz.security.user.UserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户清除事件。
 * <p>
 * 当用户从系统中清除时发布此事件，包含被清除用户的详细信息。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
public class UserClearEvent extends ApplicationEvent {

    /**
     * 被清除的用户信息。
     */
    private final UserDetails userDetails;

    /**
     * 创建一个用户清除事件。
     *
     * @param source 事件源
     * @param userDetails 被清除的用户信息
     */
    public UserClearEvent(Object source, UserDetails userDetails) {
        super(source);
        this.userDetails = userDetails;
    }

    /**
     * 获取被清除的用户信息。
     *
     * @return {@link UserDetails} 被清除的用户信息
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

}