package com.biz.trace.id;

import com.biz.common.id.SnowflakeHelper;

/**
 * 默认的日志追踪Id实现类。
 *
 * <p>该类实现了 {@link TraceIdService} 接口，使用 {@link SnowflakeHelper} 生成全局唯一的雪花ID，作为日志追踪的唯一标识符。</p>
 *
 * <p>此实现适用于大多数场景，确保每次生成的追踪Id都是唯一且有序的。</p>
 *
 * <pre>
 * 示例使用：
 * {@code
 * TraceIdService traceIdService = new DefaultTraceIdServiceImpl();
 * String traceId = traceIdService.getId();
 * }
 * </pre>
 *
 * @author francis
 * @since 2024-07-04 15:54
 * @see TraceIdService
 * @see SnowflakeHelper
 */
public class DefaultTraceIdServiceImpl implements TraceIdService {

    /**
     * 获取日志追踪Id。
     *
     * <p>该方法通过 {@link SnowflakeHelper#createDefault()} 方法生成一个全局唯一的雪花ID，并返回该ID的字符串表示。</p>
     *
     * @return 生成的唯一日志追踪Id
     */
    @Override
    public String getId() {
        return SnowflakeHelper.createDefault();
    }

}
