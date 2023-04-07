package com.biz.web.interceptor;

import com.biz.web.annotation.BizXApiCheckString;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

/**
 * 检查 入参数据 拦截器
 * 当接口参数上带有 X 注解时，判断参数是否符合 X 注解的规范
 *
 * @author francis
 * @create 2023/4/1 16:29
 */
@Order(3)
@Slf4j
public class CheckParameterHandlerInterceptor implements HandlerInterceptor, MethodInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("CustomCheckParameterHandlerInterceptor preHandle");
        // 如果不是映射到Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        log.info("Metho {}", request.getMethod());
        if (HttpMethod.POST.equals(request.getMethod())) {

        }
        // POST请求
//        if ("POST".equals(request.getMethod())) {
//
//        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        for (Parameter parameter : method.getParameters()) {
            String name = parameter.getName();
            BizXApiCheckString annotation = parameter.getAnnotation(BizXApiCheckString.class);
            Annotation[] annotations = parameter.getAnnotations();
            Class<?> type = parameter.getType();
            AnnotatedType annotatedType = parameter.getAnnotatedType();
            Annotation[] declaredAnnotations = parameter.getDeclaredAnnotations();
            Type parameterizedType = parameter.getParameterizedType();
            Executable declaringExecutable = parameter.getDeclaringExecutable();

        }
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
            Parameter parameter = methodParameter.getParameter();
            Method method1 = methodParameter.getMethod();
            Class<?> parameterType = methodParameter.getParameterType();
            AnnotatedElement annotatedElement = methodParameter.getAnnotatedElement();
            Class<?> declaringClass = methodParameter.getDeclaringClass();
            int parameterIndex = methodParameter.getParameterIndex();
            Member member = methodParameter.getMember();

        }
//        Method method = handlerMethod.getMethod();
//        BizXApiCheckString checkParams = method.getAnnotation(BizXApiCheckString.class);
//
//        request.getAttribute("password");
        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("CustomCheckTokenHandlerInterceptor postHandle");
        Map<String, Object> model = modelAndView.getModel();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("CustomCheckTokenHandlerInterceptor afterCompletion");
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        return null;
    }
}
