package com.biz.web.log.recorder;

import com.biz.web.log.Loggable;
import com.biz.web.log.handler.LogHandler;
import com.biz.web.log.handler.LogTypeHandler;
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
public class LogRecorder implements ApplicationContextAware {

    private final Map<String, LogHandler> handlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(LogTypeHandler.class);
        for (Object bean : beans.values()) {
            LogTypeHandler annotation = bean.getClass().getAnnotation(LogTypeHandler.class);
            if (annotation != null) {
                handlerMap.put(annotation.value(), (LogHandler) bean);
            }
        }
    }

    public void record(Loggable loggable, String operatorId, String operatorName, String content) {
        LogHandler handler = handlerMap.get(loggable.logType());
        if (handler != null) {
            handler.handleLog(operatorId, operatorName, content);
        } else {
            throw new IllegalArgumentException("No handler found for log type: " + loggable.logType());
        }
    }

}
