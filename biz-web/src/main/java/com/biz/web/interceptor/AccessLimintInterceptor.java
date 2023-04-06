package com.biz.web.interceptor;

import com.biz.common.singleton.SingletonMap;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 接口防刷拦截处理
 *
 * @author francis
 * @create: 2023-04-06 21:35
 **/
@Order(1)
@Slf4j
public class AccessLimintInterceptor implements HandlerInterceptor {

    /**
     * 10秒内不能
     */
    private final SingletonMap<String, AtomicLong> accessLimintCatchMap = SingletonMap.<String, AtomicLong>builder()
            .died(1000 * 10L)
            .build();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这里忽略代理软件方式访问，默认直接访问，也就是获取得到的就是访问者真实ip地址
//        String lockKey = request.getRemoteAddr() + request.getRequestURI();
//        AtomicLong atomicLong = accessLimintCatchMap.get(lockKey);
//
//        if(atomicLong.get() >= 10){
//            throw new RuntimeException("已限制访问");
//        }
//
//        // 访问数 +1
//        atomicLong.decrementAndGet();
        return true;
    }

}
