package com.biz.operation.log.handler;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.operation.log.recorder.LogRecorder;
import lombok.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志处理工厂
 *
 * @author francis
 * @since 2024-08-14 13:39
 **/
public class OperationLogHandlerFactory {

    /**
     * 业务日志处理器
     */
    private static final Map<LogTypeKey, OperationLogHandler> handlerMap = new HashMap<>();

    /**
     * 默认业务类型处理器
     */
    private final OperationLogHandler operationLogHandler;

    public OperationLogHandlerFactory(OperationLogHandler operationLogHandler) {
        this.operationLogHandler = operationLogHandler;
    }

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

    private void initOperationLogHandler() {
        // 获取所有 Bean
        for (Class<?> bean : this.getBeanAll()) {
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
                    handlerMap.put(logTypeKey, (OperationLogHandler) BizXBeanUtils.getBean(bean));
                }
            }
        }
    }


    /**
     * 获取所有 Bean
     *
     * @return
     */
    private List<Class<?>> getBeanAll() {
        List<Class<?>> classList = new ArrayList<>();
        for (String beanName : BizXBeanUtils.getBeanDefinitionNames()) {
            classList.add(BizXBeanUtils.getBean(beanName).getClass());
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

}
