package com.biz.core.interceptor;

import com.biz.core.bean.BizXComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检查 入参数据 拦截器
 * 当接口参数上带有 X 注解时，判断参数是否符合 X 注解的规范
 *
 * @author francis
 * @create 2023/4/1 16:29
 */
@Order(2)
@Slf4j
@BizXComponent
public class CustomCheckParameterHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("CustomCheckParameterHandlerInterceptor preHandle");
        // 如果不是映射到Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

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
