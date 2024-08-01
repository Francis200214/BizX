package com.biz.web.log.recorder;

import com.biz.common.utils.Common;
import com.biz.web.log.Loggable;
import com.biz.web.log.handler.LogTypeHandler;
import com.biz.web.log.handler.TraceLogHandler;
import com.biz.web.log.operation.DefaultOperationLogHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志记录器
 *
 * @author francis
 * @since 2024-05-31 16:30
 **/
@Slf4j
public class LogRecorder implements ApplicationContextAware {

    /**
     * 业务日志处理器
     */
    private static final Map<LogTypeKey, TraceLogHandler> handlerMap = new HashMap<>();

    /**
     * 默认业务类型处理器
     */
    private DefaultOperationLogHandler defaultOperationLogHandler;


    /**
     * 记录日志
     *
     * @param traceId      日志追踪链路Id
     * @param loggable     业务日志
     * @param operatorId   操作人
     * @param operatorName 操作人名称
     * @param content      操作日志内容
     * @param e            错误堆栈信息（只有当有错误信息时才会传递该参数，否则为null）
     */
    public void record(String traceId, Loggable loggable, String operatorId, String operatorName, String content, Throwable e) {
        TraceLogHandler handler = handlerMap.get(LogTypeKey.builder()
                .logLargeType(loggable.logLargeType())
                .logSmallType(loggable.logSmallType())
                .build());
        if (handler != null) {
            handler.handleLog(traceId, loggable.logLargeType(), loggable.logSmallType(), operatorId, operatorName, content, e);

        } else {
            this.defaultHandler(traceId, loggable, operatorId, operatorName, content, e);
        }
    }


    /**
     * 默认处理
     *
     * @param traceId      日志追踪链路Id
     * @param loggable     业务日志
     * @param operatorId   操作人
     * @param operatorName 操作人名称
     * @param content      操作日志内容
     * @param e            错误堆栈信息（只有当有错误信息时才会传递该参数，否则为null）
     */
    private void defaultHandler(String traceId, Loggable loggable, String operatorId, String operatorName, String content, Throwable e) {
        if (defaultOperationLogHandler != null) {
            // 有默认的操作日志实现类，则推送信息
            defaultOperationLogHandler.push(traceId, loggable.logLargeType(), loggable.logSmallType(), operatorId, operatorName, content, e);
        }
    }

    /**
     * 获取所有 Bean
     *
     * @param applicationContext
     * @return
     */
    private List<Class<?>> getBeanAll(ApplicationContext applicationContext) {
        List<Class<?>> classList = new ArrayList<>();
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            classList.add(applicationContext.getBean(beanName).getClass());
        }
        return classList;
    }

    /**
     * 检查类是否包含注解
     *
     * @param clazz
     * @param annotation
     * @return
     */
    private static boolean checkAnnotationInClass(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * 获取类上的注解
     *
     * @param clazz
     * @param annotation
     * @return
     */
    private static <A extends Annotation> A getAnnotationInClass(Class<?> clazz, Class<A> annotation) {
        return clazz.getAnnotation(annotation);
    }

    /**
     * 日志类型 key
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
         * 日志大类型
         */
        private String logLargeType;

        /**
         * 日志小类型
         */
        private String logSmallType;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取所有 Bean
        for (Class<?> bean : this.getBeanAll(applicationContext)) {
            if (checkAnnotationInClass(bean, LogTypeHandler.class)) {
                LogTypeHandler logTypeHandler = getAnnotationInClass(bean, LogTypeHandler.class);
                if (logTypeHandler != null) {
                    if (Common.isBlank(logTypeHandler.largeType())) {
                        throw new RuntimeException("LogTypeHandler 注解的 largeType 不能为空");
                    }
                    if (Common.isBlank(logTypeHandler.smallType())) {
                        throw new RuntimeException("LogTypeHandler 注解的 smallType 不能为空");
                    }

                    LogTypeKey logTypeKey = LogTypeKey.builder()
                            .logLargeType(logTypeHandler.largeType())
                            .logSmallType(logTypeHandler.smallType())
                            .build();
                    if (handlerMap.containsKey(logTypeKey)) {
                        throw new RuntimeException("LogTypeHandler 注解的 largeType 和 smallType 不能重复");
                    }
                    handlerMap.put(logTypeKey,
                            (TraceLogHandler) applicationContext.getBean(bean));
                }
            }
        }

        try {
            this.defaultOperationLogHandler = applicationContext.getBean(DefaultOperationLogHandler.class);
        } catch (Exception e) {
            log.warn("没有找到 DefaultOperationLogHandler Bean");
        }
    }


}
