package com.biz.web.interceptor;

import com.biz.library.web.BizVerification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 检查 Token 拦截器
 * 当接口上带有 X 注解时，判断接口入参Header中的token值是否在该系统中存在
 *
 * @author francis
 * @create 2023/4/1 16:29
 */
@Order(2)
@Slf4j
public class CheckTokenHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("CustomCheckTokenHandlerInterceptor preHandle");
        // 如果不是映射到Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        BizVerification verification = method.getAnnotation(BizVerification.class);
        if (verification == null) {
            return true;
        }

        String token = request.getHeader("biz-token");
        if (token == null) {
            throw new RuntimeException("token is null");
        }
        // TODO 处理当前用户的角色


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("CustomCheckTokenHandlerInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("CustomCheckTokenHandlerInterceptor afterCompletion");
    }

}
