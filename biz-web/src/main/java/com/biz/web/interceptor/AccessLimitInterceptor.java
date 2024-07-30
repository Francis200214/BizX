package com.biz.web.interceptor;

import com.biz.web.annotation.BizXAccessLimit;
import com.biz.web.interceptor.accessLimit.AccessLimitCatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接口防刷拦截处理
 *
 * @author francis
 * @create: 2023-04-06 21:35
 **/
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor, Ordered {


    private AccessLimitCatchService accessLimitCatchService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到Controller方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        BizXAccessLimit annotation = method.getAnnotation(BizXAccessLimit.class);
        if (annotation == null) {
            return true;
        }

        // 这里忽略代理软件方式访问，默认直接访问，也就是获取得到的就是访问者真实ip地址
        AtomicInteger accessNumber = accessLimitCatchService.getAccessNumber(request.getRemoteAddr(), request.getRequestURI());
        return accessNumber.get() <= 10;
    }

    @Override
    public int getOrder() {
        return 102;
    }
}
