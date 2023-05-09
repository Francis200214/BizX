package com.biz.web.interceptor;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.web.rbac.BizVerification;
import com.biz.web.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
@Slf4j
public class CheckTokenHandlerInterceptor implements HandlerInterceptor, ApplicationListener<ContextRefreshedEvent> {

    private static Token token;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("CheckTokenHandlerInterceptor preHandle");
        // Token 中设置 HttpServletResponse
        setHttpServletResponse(response);

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

        // 根据请求头设置当前用户的信息
        setAccountSession(request);
        // 处理当前用户的角色

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("CheckTokenHandlerInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("CheckTokenHandlerInterceptor afterCompletion");
    }


    /**
     * 初始化
     * @param response
     */
    private void setHttpServletResponse(HttpServletResponse response) {
        token.setHttpServletResponse(response);
    }

    private void setAccountSession(HttpServletRequest request) {
        String header = request.getHeader("Biz-Token");
        if (Common.isBlank(header)) {
            throw new RuntimeException("token is null");
        }
        token.initAccount(header);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            token = BizXBeanUtils.getBean(Token.class);
        } catch (Exception e) {
            throw new RuntimeException("Token bean is not definition");
        }
    }
}
