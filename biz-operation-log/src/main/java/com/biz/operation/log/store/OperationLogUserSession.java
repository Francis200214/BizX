package com.biz.operation.log.store;

/**
 * {@code OperationLogUserSession}接口定义了操作日志中当前用户信息的存储和管理方法。
 *
 * <p>该接口主要用于获取当前操作用户的ID和姓名，以及提供清除用户信息的功能。</p>
 *
 * <p>主要方法包括：</p>
 * <ul>
 *     <li>{@link #getOperationUserId()}：获取当前操作用户的唯一标识符（ID）。</li>
 *     <li>{@link #getOperationUserName()}：获取当前操作用户的姓名。</li>
 *     <li>{@link #removeAll()}：清除存储中的所有用户信息。</li>
 * </ul>
 *
 * <p>该接口通常用于操作日志的记录过程中，以确保日志中包含正确的用户信息。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
public interface OperationLogUserSession {

    /**
     * 获取当前操作用户的唯一标识符（ID）。
     *
     * @return 操作用户的ID
     */
    String getOperationUserId();

    /**
     * 获取当前操作用户的姓名。
     *
     * @return 操作用户的姓名
     */
    String getOperationUserName();

    /**
     * 清除存储中的所有用户信息。
     */
    void removeAll();

}
