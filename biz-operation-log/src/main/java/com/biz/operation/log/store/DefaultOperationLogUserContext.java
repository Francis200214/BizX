package com.biz.operation.log.store;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code DefaultOperationLogUserContext}类是{@link OperationLogUserContext}接口的默认实现。
 *
 * <p>该实现使用{@link ThreadLocal}来存储和管理当前默认地操作用户的ID和姓名。通过这种方式，
 * 可以确保在同一线程内安全地访问和修改操作用户的信息。</p>
 *
 * @author francis
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class DefaultOperationLogUserContext implements OperationLogUserContext {


    /**
     * 获取当前操作用户的唯一标识符（ID）。
     *
     * @return 默认为空字符串
     */
    @Override
    public String getOperationUserId() {
        return "";
    }

    /**
     * 获取当前操作用户的姓名。
     *
     * @return 默认为空字符串
     */
    @Override
    public String getOperationUserName() {
        return "";
    }

}
