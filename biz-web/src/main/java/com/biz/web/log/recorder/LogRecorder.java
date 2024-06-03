package com.biz.web.log.recorder;

import com.biz.web.log.Loggable;
import com.biz.web.log.handler.LogHandler;
import com.biz.web.log.handler.LogLargeTypeDefaultHandler;
import com.biz.web.log.handler.LogTypeHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志记录器
 *
 * @author francis
 * @create 2024-05-31 16:30
 **/
@Component
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
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(LogTypeHandler.class);
        for (Object bean : beans.values()) {
            // 精准业务类型处理器
            LogTypeHandler logTypeHandler = bean.getClass().getAnnotation(LogTypeHandler.class);
            if (logTypeHandler != null) {
                handlerMap.put(
                        LogTypeKey.builder()
                                .logLargeType(logTypeHandler.largeType())
                                .logSmallType(logTypeHandler.smallType())
                                .build()
                        , (LogHandler) bean);
            }

            // 默认大业务类型处理器
            LogLargeTypeDefaultHandler largeTypeDefaultHandler = bean.getClass().getAnnotation(LogLargeTypeDefaultHandler.class);
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
            handler.handleLog(operatorId, operatorName, content, e);

        } else {
            if (largeTypeDefaultHandlerMap.containsKey(loggable.logLargeType())) {
                largeTypeDefaultHandlerMap.get(loggable.logLargeType())
                        .handleLog(operatorId, operatorName, content, e);

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
