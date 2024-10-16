package com.biz.operation.log.store;

import org.aspectj.lang.JoinPoint;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作日志存储内容。
 *
 * <p>
 * 主要存储当前线程的操作日志信息。
 * </p>
 *
 * @author francis
 * @create 2024-09-19
 * @since 1.0.1
 **/
public class OperationLogContextHolder {

    /**
     * 当前线程操作日志上下文。
     */
    private static final InheritableThreadLocal<ConcurrentHashMap<OperationLogStoreKey, OperationLogContext>> OPERATION_LOG_CONTEXT = new InheritableThreadLocal<>();

    /**
     * 获取当前线程操作日志上下文。
     *
     * <p>
     * 根据 {@link OperationLogStoreKey} 获取对应的操作日志上下文。
     * </p>
     *
     * @param key 操作日志上下文键。
     * @return 操作日志上下文。
     */
    public static OperationLogContext getOperationLogContext(OperationLogStoreKey key) {
        ConcurrentHashMap<OperationLogStoreKey, OperationLogContext> map = getContextMap();
        if (map == null) {
            throw new IllegalStateException("当前线程未设置操作日志上下文，请先调用 OperationLogContextHolder.setOperationLogContext 方法设置操作日志上下文。");
        }
        return map.get(key);
    }

    /**
     * 设置当前线程操作日志上下文。
     *
     * <p>
     *     指定 {@link OperationLogStoreKey} 和 {@link OperationLogContext} 对应内容。
     * </p>
     *
     * @param key   操作日志上下文键。
     * @param context 操作日志上下文。
     */
    public static void setOperationLogContext(OperationLogStoreKey key, OperationLogContext context) {
        ConcurrentHashMap<OperationLogStoreKey, OperationLogContext> map = OPERATION_LOG_CONTEXT.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        map.put(key, context);
        OPERATION_LOG_CONTEXT.set(map);
    }

    /**
     * 构建操作日志上下文键。
     *
     * <p>
     *     返回一个 {@link OperationLogStoreKey} 对象，用于存储操作日志上下文键。
     * </p>
     *
     * @param joinPoint 切点信息。
     * @return 操作日志上下文键。
     */
    public static OperationLogStoreKey buildOperationLogStoreKey(JoinPoint joinPoint) {
        return OperationLogStoreKey.builder()
                .className(joinPoint.getTarget().getClass().getName())
                .methodName(joinPoint.getSignature().getName())
                .build();
    }

    /**
     * 构建操作日志上下文。
     *
     * <p>
     *     返回一个 {@link OperationLogContext} 对象，用于存储操作日志内容。
     * </p>
     *
     * @param content 操作日志内容。
     * @return 操作日志上下文。
     */
    public static OperationLogContext buildOperationLogContent(String content) {
        return OperationLogContext.builder()
                .content(content)
                .build();
    }


    /**
     * 检查当前线程是否设置了操作日志上下文。
     */
    private static void checkContext() {
        if (OPERATION_LOG_CONTEXT.get() == null) {
            throw new IllegalStateException("当前线程未设置操作日志上下文，请先调用 OperationLogContextHolder.setOperationLogContext 方法设置操作日志上下文。");
        }
    }


    /**
     * 获取当前线程的操作日志上下文。
     */
    private static ConcurrentHashMap<OperationLogStoreKey, OperationLogContext> getContextMap() {
        checkContext();
        return OPERATION_LOG_CONTEXT.get();
    }

}
