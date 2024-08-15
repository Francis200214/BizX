package com.biz.operation.log.handler;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import lombok.*;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code OperationLogHandlerFactory}类是一个日志处理工厂类，用于管理和提供不同类型的日志处理器。
 *
 * <p>该类通过维护一个日志处理器映射表，允许根据日志的大类型和小类型获取对应的日志处理器。
 * 如果没有找到匹配的处理器，则返回默认的日志处理器。</p>
 *
 * <p>类的主要职责包括：</p>
 * <ul>
 *     <li>初始化日志处理器并将其注册到映射表中。</li>
 *     <li>根据日志类型获取对应的日志处理器。</li>
 * </ul>
 *
 * <p>该类还包含一个内部静态类{@link LogTypeKey}，用于作为日志处理器映射表的键。</p>
 *
 * <p>通过实现 {@link SmartInitializingSingleton} 接口，该类在所有单例 Bean 完成初始化后，
 * 会执行 {@link #afterSingletonsInstantiated()} 方法，用于初始化日志处理器的映射表。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 **/
public class OperationLogHandlerFactory implements SmartInitializingSingleton {

    /**
     * 存储业务日志处理器的映射表，键为日志类型，值为对应的处理器。
     */
    private static final Map<LogTypeKey, OperationLogHandler> handlerMap = new HashMap<>();

    /**
     * 默认的业务日志处理器，当没有找到匹配的日志处理器时使用。
     */
    private final OperationLogHandler operationLogHandler;

    /**
     * 构造一个新的{@code OperationLogHandlerFactory}实例。
     *
     * @param operationLogHandler 默认的业务日志处理器
     */
    public OperationLogHandlerFactory(OperationLogHandler operationLogHandler) {
        this.operationLogHandler = operationLogHandler;
    }

    /**
     * 根据日志的大类型和小类型获取对应的日志处理器。
     *
     * <p>如果找不到对应的处理器，将返回默认的日志处理器。</p>
     *
     * @param logLargeType 日志的大类型
     * @param logSmallType 日志的小类型
     * @return 对应的日志处理器，如果找不到则返回默认处理器
     */
    public OperationLogHandler getOperationLogHandler(String logLargeType, String logSmallType) {
        LogTypeKey logTypeKey = LogTypeKey.builder()
                .logLargeType(logLargeType)
                .logSmallType(logSmallType)
                .build();
        if (handlerMap.containsKey(logTypeKey)) {
            return handlerMap.get(logTypeKey);
        }
        return operationLogHandler;
    }

    /**
     * 当所有 Bean 都加载完成后，初始化并注册日志处理器。
     *
     * <p>该方法会扫描所有的 Bean，并根据类上的 {@link LogTypeAnnotation} 注解进行注册。</p>
     *
     * @throws RuntimeException 如果 {@link LogTypeAnnotation} 注解的 largeType 或 smallType 为空，
     *                          或者有重复的类型定义，将抛出此异常
     */
    @Override
    public void afterSingletonsInstantiated() {
        // 获取所有 Bean
        for (Class<?> bean : this.getBeanAll()) {
            if (checkAnnotationInClass(bean, LogTypeAnnotation.class)) {
                LogTypeAnnotation logTypeAnnotation = getAnnotationInClass(bean, LogTypeAnnotation.class);
                if (logTypeAnnotation != null) {
                    if (Common.isBlank(logTypeAnnotation.largeType())) {
                        throw new RuntimeException("LogTypeAnnotation 注解的 largeType 不能为空");
                    }
                    if (Common.isBlank(logTypeAnnotation.smallType())) {
                        throw new RuntimeException("LogTypeAnnotation 注解的 smallType 不能为空");
                    }

                    LogTypeKey logTypeKey = LogTypeKey.builder()
                            .logLargeType(logTypeAnnotation.largeType())
                            .logSmallType(logTypeAnnotation.smallType())
                            .build();
                    if (handlerMap.containsKey(logTypeKey)) {
                        throw new RuntimeException("LogTypeAnnotation 注解的 largeType 和 smallType 不能重复");
                    }
                    handlerMap.put(logTypeKey, (OperationLogHandler) BizXBeanUtils.getBean(bean));
                }
            }
        }
    }

    /**
     * 获取所有 Bean 的类列表。
     *
     * @return 包含所有 Bean 类的列表
     */
    private List<Class<?>> getBeanAll() {
        List<Class<?>> classList = new ArrayList<>();
        for (String beanName : BizXBeanUtils.getBeanDefinitionNames()) {
            classList.add(BizXBeanUtils.getBean(beanName).getClass());
        }
        return classList;
    }

    /**
     * 检查指定的类是否包含特定的注解。
     *
     * @param clazz 要检查的类
     * @param annotation 要查找的注解类型
     * @return 如果类包含指定的注解，返回 {@code true}，否则返回 {@code false}
     */
    private static boolean checkAnnotationInClass(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * 获取指定类上的特定注解实例。
     *
     * @param clazz 要检查的类
     * @param annotation 要获取的注解类型
     * @param <A> 注解类型的泛型
     * @return 注解实例，如果类上不存在该注解则返回 {@code null}
     */
    private static <A extends Annotation> A getAnnotationInClass(Class<?> clazz, Class<A> annotation) {
        return clazz.getAnnotation(annotation);
    }

    /**
     * {@code LogTypeKey}类用于作为日志处理器映射表的键，包含日志的大类型和小类型。
     */
    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class LogTypeKey {
        /**
         * 日志的大类型。
         */
        private String logLargeType;

        /**
         * 日志的小类型。
         */
        private String logSmallType;
    }
}
