package com.biz.operation.log.store;

import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code DefaultOperationLogUserSession}类是{@link OperationLogUserSession}接口的默认实现。
 *
 * <p>该实现使用{@link ThreadLocal}来存储和管理当前操作用户的ID和姓名。通过这种方式，
 * 可以确保在同一线程内安全地访问和修改操作用户的信息。</p>
 *
 * <p>类的主要职责包括：</p>
 * <ul>
 *     <li>获取当前操作用户的ID和姓名，如果未设置则返回空字符串。</li>
 *     <li>提供方法清除存储的用户信息。</li>
 * </ul>
 *
 * <p>实现细节：</p>
 * <ul>
 *     <li>如果用户ID或姓名为空白（null或空字符串），则将其设置为默认的空字符串。</li>
 *     <li>使用{@link ThreadLocal#remove()}方法清除存储在线程本地变量中的数据，以避免内存泄漏。</li>
 * </ul>
 *
 * <p>通过使用{@link Slf4j}注解，类中提供了日志记录功能，用于在清除数据失败时记录错误信息。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
@Slf4j
public class DefaultOperationLogUserSession implements OperationLogUserSession {

    /**
     * 当前操作用户的ID，使用{@link ThreadLocal}来存储。
     */
    private static final ThreadLocal<String> operatorIdHolder = new ThreadLocal<>();

    /**
     * 当前操作用户的姓名，使用{@link ThreadLocal}来存储。
     */
    private static final ThreadLocal<String> operatorNameHolder = new ThreadLocal<>();

    /**
     * 获取当前操作用户的唯一标识符（ID）。
     *
     * <p>如果ID为空白，则将其设置为空字符串。</p>
     *
     * @return 操作用户的ID
     */
    @Override
    public String getOperationUserId() {
        String userId = operatorIdHolder.get();
        if (Common.isBlank(userId)) {
            operatorIdHolder.set("");
            return operatorIdHolder.get();
        }

        return userId;
    }

    /**
     * 获取当前操作用户的姓名。
     *
     * <p>如果姓名为空白，则将其设置为空字符串。</p>
     *
     * @return 操作用户的姓名
     */
    @Override
    public String getOperationUserName() {
        String userName = operatorNameHolder.get();
        if (Common.isBlank(userName)) {
            operatorNameHolder.set("");
            return operatorNameHolder.get();
        }

        return userName;
    }

    /**
     * 清除存储中的所有用户信息。
     *
     * <p>该方法会清除当前线程中存储的用户ID和姓名。如果清除过程中出现异常，将记录错误日志。</p>
     */
    @Override
    public void removeAll() {
        try {
            operatorIdHolder.remove();
            operatorNameHolder.remove();
        } catch (Exception e) {
            log.error("移除 AccountHolder OperatorIdHolder 和 OperatorNameHolder 失败", e);
        }
    }
}
