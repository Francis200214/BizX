package com.biz.web.log.recorder;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.web.log.Loggable;
import com.biz.web.log.handler.LogHandler;
import com.biz.web.log.handler.LogLargeTypeDefaultHandler;
import com.biz.web.log.handler.LogTypeHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
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
    private final Map<LogTypeKey, LogHandler> handlerMap = new HashMap<>();

    /**
     * 大业务类型默认处理器
     */
    private final Map<String, LogHandler> largeTypeDefaultHandlerMap = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取所有 Bean
        for (Object bean : BizXBeanUtils.getBeanDefinitionClasses()) {
            Class<?> targetClass = bean.getClass();

            // 检查实现的接口
            Class<?>[] interfaces = targetClass.getInterfaces();
            for (Class<?> iface : interfaces) {
                scanMethods(bean, iface);
            }

            // 检查类自身的方法
            scanMethods(bean, targetClass);
        }
    }

    private void scanMethods(Object bean, Class<?> clazz) {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz); // 获取所有方法
        for (Method method : methods) {
            LogTypeHandler logTypeHandler = AnnotationUtils.findAnnotation(method, LogTypeHandler.class);
            if (logTypeHandler != null) {
                handlerMap.put(
                        LogTypeKey.builder()
                                .logLargeType(logTypeHandler.largeType())
                                .logSmallType(logTypeHandler.smallType())
                                .build(),
                        (LogHandler) bean);
            }

            LogLargeTypeDefaultHandler largeTypeDefaultHandler = AnnotationUtils.findAnnotation(method, LogLargeTypeDefaultHandler.class);
            if (largeTypeDefaultHandler != null) {
                largeTypeDefaultHandlerMap.put(largeTypeDefaultHandler.value(), (LogHandler) bean);
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
