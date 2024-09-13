package com.demo.log;

import com.biz.operation.log.store.OperationLogUserContext;
import org.springframework.stereotype.Component;

/**
 * 操作日志当前用户信息实现
 *
 * @author francis
 **/
@Component
public class OperationLogUserContextImpl implements OperationLogUserContext {

    @Override
    public String getOperationUserId() {
        // 获取当前用户Id
        return "";
    }

    @Override
    public String getOperationUserName() {
        // 获取当前用户名称
        return "";
    }

}
