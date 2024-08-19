package com.biz.trace.id;

/**
 * 日志追踪Id服务接口。
 *
 * <p>该接口定义了获取日志追踪Id的方法，用于链路追踪过程中标识唯一请求或操作。</p>
 *
 * <p>实现该接口的类应提供生成唯一追踪Id的逻辑，通常用于分布式系统中进行请求的追踪和日志的关联。</p>
 *
 * <pre>
 * 示例使用：
 * {@code
 * TraceIdService traceIdService = ...;
 * String traceId = traceIdService.getId();
 * }
 * </pre>
 *
 * @author francis
 * @since 1.0.1
 */
public interface TraceIdService {

    /**
     * 获取日志追踪Id。
     *
     * <p>每次调用该方法应返回一个唯一的Id，用于标识单次请求或操作的追踪信息。</p>
     * <p>Id的长度为18位。</p>
     *
     * @return 唯一的日志追踪Id
     */
    String getId();

}
