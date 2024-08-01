package com.biz.web.log.trace;


/**
 * 日志跟踪用户信息服务
 *
 * @author francis
 * @since 2024-07-02 13:52
 **/
public interface TraceLogUserService {

    /**
     * 获取当前用户的ID
     */
    String getId();


    /**
     * 获取当前用户的名称
     */
    String getName();

}
