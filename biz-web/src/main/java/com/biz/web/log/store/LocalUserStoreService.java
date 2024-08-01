package com.biz.web.log.store;

/**
 * 当前用户存储
 *
 * @author francis
 * @since 2024-07-04 17:40
 **/
public interface LocalUserStoreService {

    /**
     * 获取操作人id
     */
    String getOperationUserId();

    /**
     * 获取操作人姓名
     */
    String getOperationUserName();

    /**
     * 清除所有
     */
    void removeAll();

}
