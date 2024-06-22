package com.biz.web.log.recorder;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.utils.Common;
import com.biz.web.log.Loggable;
import com.biz.web.log.handler.LogHandler;
import com.biz.web.log.handler.LogLargeTypeDefaultHandler;
import com.biz.web.log.handler.LogTypeHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录器
 *
 * @author francis
 * @create 2024-05-31 16:30
 **/
@Slf4j
public class LogRecorder implements ApplicationContextAware {

    /**
     * 精准业务类型处理器
     */
    private static final Map<LogTypeKey, LogHandler> handlerMap = new HashMap<>();

    /**
     * 大业务类型默认处理器
     */
    private static final Map<String, LogHandler> largeTypeDefaultHandlerMap = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取所有 Bean
        for (Class<?> bean : BizXBeanUtils.getBeanDefinitionClasses()) {
            if (ReflectionUtils.checkAnnotationInClass(bean, LogLargeTypeDefaultHandler.class)) {
                LogLargeTypeDefaultHandler largeTypeDefaultHandler = ReflectionUtils.getAnnotationInClass(bean, LogLargeTypeDefaultHandler.class);
                if (largeTypeDefaultHandler != null) {
                    if (Common.isBlank(largeTypeDefaultHandler.value())) {
                        throw new RuntimeException("LogLargeTypeDefaultHandler 注解的 value 不能为空");
                    }
                    if (largeTypeDefaultHandlerMap.containsKey(largeTypeDefaultHandler.value())) {
                        throw new RuntimeException("LogLargeTypeDefaultHandler 注解的 value 不能重复");
                    }
                    largeTypeDefaultHandlerMap.put(largeTypeDefaultHandler.value(), (LogHandler) applicationContext.getBean(bean));
                }
            }

            if (ReflectionUtils.checkAnnotationInClass(bean, LogTypeHandler.class)) {
                LogTypeHandler logTypeHandler = ReflectionUtils.getAnnotationInClass(bean, LogTypeHandler.class);
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
                            (LogHandler) applicationContext.getBean(bean));
                }
            }
        }
    }


    public void record(Loggable loggable, String operatorId, String operatorName, String content, Throwable e) {
        LogHandler handler = handlerMap.get(LogTypeKey.builder()
                .logLargeType(loggable.logLargeType())
                .logSmallType(loggable.logSmallType())
                .build());
        if (handler != null) {
            handler.handleLog(loggable.logLargeType(), loggable.logSmallType(), operatorId, operatorName, content, e);

        } else {
            if (largeTypeDefaultHandlerMap.containsKey(loggable.logLargeType())) {
                largeTypeDefaultHandlerMap.get(loggable.logLargeType())
                        .handleLog(loggable.logLargeType(), loggable.logSmallType(), operatorId, operatorName, content, e);

            } else {
                log.error("No handler found for log type: largeType {} smallType {}", loggable.logLargeType(), loggable.logSmallType());

            }
        }
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


}
