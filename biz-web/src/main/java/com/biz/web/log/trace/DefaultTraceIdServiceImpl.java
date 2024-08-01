package com.biz.web.log.trace;


import com.biz.common.id.SnowflakeHelper;

/**
 * 默认日志追踪Id实现
 *
 * @author francis
 * @since 2024-07-04 15:54
 **/
public class DefaultTraceIdServiceImpl implements TraceIdService {

    @Override
    public String getId() {
        return SnowflakeHelper.createDefault();
    }

}
