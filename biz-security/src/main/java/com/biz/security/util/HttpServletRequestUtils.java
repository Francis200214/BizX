package com.biz.security.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * HttpServletRequest 对象工具类。
 * <p>
 * 提供用于处理 HttpServletRequest 对象的工具方法，包括获取请求对应的处理方法（HandlerMethod）和注解信息。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-11
 */
public final class HttpServletRequestUtils {

    /**
     * 获取 HttpServletRequest 对应的 HandlerMethod。
     *
     * @param request {@link HttpServletRequest} 对象
     * @return 请求对应的 {@link HandlerMethod}，如果找不到处理方法则返回 {@code null}
     */
    public static HandlerMethod getHandlerMethod(HttpServletRequest request) {
        // 获取 Spring 应用上下文
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());

        // 从上下文中获取所有的 HandlerMapping 实例
        Map<String, HandlerMapping> handlerMappings = context.getBeansOfType(HandlerMapping.class);

        // 遍历所有 HandlerMapping 找到处理请求的 HandlerMethod
        try {
            for (HandlerMapping handlerMapping : handlerMappings.values()) {
                Object handler = Objects.requireNonNull(handlerMapping.getHandler(request)).getHandler();
                if (handler instanceof HandlerMethod) {
                    return (HandlerMethod) handler;
                }
            }
        } catch (Exception e) {
            // 在没有找到匹配的方法时，返回 null
            return null;
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