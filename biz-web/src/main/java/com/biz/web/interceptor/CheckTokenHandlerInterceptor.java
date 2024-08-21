package com.biz.web.interceptor;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.utils.Common;
import com.biz.web.error.ErrorCode;
import com.biz.web.error.IF;
import com.biz.web.rbac.BizAccessAllow;
import com.biz.web.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 检查 Token 拦截器
 * 当接口上带有 X 注解时，判断接口入参Header中的token值是否在该系统中存在
 *
 * @author francis
 * @since 1.0.1
 */
@Slf4j
public class CheckTokenHandlerInterceptor implements HandlerInterceptor, ApplicationListener<ContextRefreshedEvent>, Ordered {

    private static Token token;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Token 中设置 HttpServletResponse
        this.setHttpServletResponse(response);
        // 如果不是映射到Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 检查类或方法上是否有注解
        if (!checkAccessAllow(handler)) {
            return true;
        }

        // 根据请求头设置当前用户的信息
        this.setAccountSession(request);
        // 检查Token是否失效
        this.checkTokenExpire();
        return true;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            token = BizXBeanUtils.getBean(Token.class);
        } catch (Exception e) {
            throw new RuntimeException("Token bean is not definition");
        }
    }

    @Override
    public int getOrder() {
        return 100;
    }


    private boolean checkAccessAllow(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        BizAccessAllow annotation = method.getAnnotation(BizAccessAllow.class);
        if (annotation == null) {
            // 检查当前方法的class是否存在BizAccessAllow注解
            Class<?> declaringClass = method.getDeclaringClass();
            BizAccessAllow accessAllow = declaringClass.getAnnotation(BizAccessAllow.class);
            if (accessAllow == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化 HttpServletResponse
     *
     * @param response
     */
    private void setHttpServletResponse(HttpServletResponse response) {
        if (token != null) {
            token.setHttpServletResponse(response);
        }
    }

    /**
     * 获取请求头中 header 的 token 值
     * @param request
     */
    private void setAccountSession(HttpServletRequest request) {
        String header = request.getHeader("Biz-Token");
        if (!Common.isBlank(header)) {
            token.initAccount(header);
        }
    }

    private void checkTokenExpire() {
        IF.is(token.checkTokenIsExpire(), ErrorCode.NOT_LOGIN);
    }

}
