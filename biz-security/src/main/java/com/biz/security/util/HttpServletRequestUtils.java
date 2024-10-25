package com.biz.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * HttpServletRequest 对象工具类。
 *
 * 提供用于处理 HttpServletRequest 对象的工具方法，包括获取请求对应的处理方法（HandlerMethod）和注解信息。
 */
@Slf4j
public final class HttpServletRequestUtils {

    /**
     * 获取 HttpServletRequest 对应的 HandlerMethod。
     *
     * @param request {@link HttpServletRequest} 对象
     * @return 请求对应的 {@link HandlerMethod}，如果找不到处理方法则返回 {@code null}
     */
    public static HandlerMethod getHandlerMethod(HttpServletRequest request) {
        // 获取 Spring 应用上下文
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

        if (context == null) {
            // 日志记录上下文为空的情况
            if (log.isDebugEnabled()) {
                log.debug("Spring 应用上下文为空, 无法获取到对于的接口");
            }
            return null;
        }

        // 从上下文中获取所有的 HandlerMapping 实例
        Map<String, HandlerMapping> handlerMappings = context.getBeansOfType(HandlerMapping.class);

        // 手动解析请求路径并缓存
        try {
            ServletRequestPathUtils.parseAndCache(request);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("解析请求路径时发生错误: {}", e.getMessage());
            }
            return null;
        }

        // 遍历所有 HandlerMapping 找到处理请求的 HandlerMethod
        try {
            for (HandlerMapping handlerMapping : handlerMappings.values()) {
                HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
                if (handlerExecutionChain == null) {
                    continue;
                }
                Object handler = handlerExecutionChain.getHandler();
                if (handler instanceof HandlerMethod) {
                    return (HandlerMethod) handler;
                }
            }
        } catch (Exception e) {
            // 日志记录异常详细信息
            if (log.isDebugEnabled()) {
                log.debug("获取路径对于处理接口时发生错误: {}", e.getMessage());
            }
        }

        return null;
    }

    /**
     * 获取 HttpServletRequest 对应的注解。
     *
     * @param request {@link HttpServletRequest} 对象
     * @param annotationClass 注解的类型
     * @return 请求对应的 {@link Annotation}，如果找不到注解则返回 {@code null}
     */
    public static Annotation getAnnotation(HttpServletRequest request, Class<? extends Annotation> annotationClass) {
        HandlerMethod handlerMethod = getHandlerMethod(request);
        if (handlerMethod == null) {
            return null;
        }
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(annotationClass)) {
            return method.getAnnotation(annotationClass);
        }
        return null;
    }
}
