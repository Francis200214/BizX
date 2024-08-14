package com.biz.operation.log.store;

import com.biz.common.utils.Common;
import com.biz.operation.log.trace.TraceLogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Optional;


/**
 * 默认当前用户存储
 *
 * @author francis
 * @since 2024-07-04 17:40
 **/
@Slf4j
public class DefaultOperationLogUserStore implements OperationLogUserStore, ApplicationContextAware {

    /**
     * 当前操作人id
     */
    private static final ThreadLocal<String> operatorIdHolder = new ThreadLocal<>();

    /**
     * 当前操作人姓名
     */
    private static final ThreadLocal<String> operatorNameHolder = new ThreadLocal<>();

    /**
     * 日志跟踪用户信息
     */
    private TraceLogUserService traceLogUserService;


    @Override
    public String getOperationUserId() {
        String userId = operatorIdHolder.get();
        if (Common.isBlank(userId)) {
            if (traceLogUserService == null) {
                operatorIdHolder.set("");

            } else {
                operatorIdHolder.set(Optional.ofNullable(traceLogUserService.getId()).orElse(""));

            }
            return operatorIdHolder.get();
        }

        return userId;
    }

    @Override
    public String getOperationUserName() {
        String userName = operatorNameHolder.get();
        if (Common.isBlank(userName)) {
            if (traceLogUserService == null) {
                operatorNameHolder.set("");

            } else {
                operatorNameHolder.set(Optional.ofNullable(traceLogUserService.getName()).orElse(""));

            }
            return operatorNameHolder.get();
        }

        return userName;
    }


    @Override
    public void removeAll() {
        try {
            operatorIdHolder.remove();
            operatorNameHolder.remove();
        } catch (Exception e) {
            log.error("移除 AccountHolder OperatorIdHolder And OperatorNameHolder 失败", e);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            this.traceLogUserService = applicationContext.getBean(TraceLogUserService.class);
        } catch (Exception e) {
            log.error("Not found TraceLogUserService Bean Or Subjoin Bean in TraceLogAspect");
        }
    }

}
