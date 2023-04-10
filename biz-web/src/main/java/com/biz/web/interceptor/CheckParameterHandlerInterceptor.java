package com.biz.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class CheckParameterHandlerInterceptor implements HandlerInterceptor {

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
        Map<String, Object> model = modelAndView.getModel();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("CustomCheckTokenHandlerInterceptor afterCompletion");
    }

}
