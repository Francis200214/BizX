package com.biz.web.log.handler;

/**
 * 日志处理器
 *
 * @author francis
 * @create 2024-05-31 16:28
 **/
public interface LogHandler {

    /**
     * 日志处理
     *
     * @param logLargeType 业务日志大类型
     * @param logSmallType 业务日志小类型
     * @param operatorId   操作人
     * @param operatorName 操作人名称
     * @param content      操作日志内容
     * @param e            错误堆栈信息（只有当有错误信息时才会传递该参数，否则为null）
     */
    void handleLog(String logLargeType, String logSmallType, String operatorId, String operatorName, String content, Throwable e);

}
