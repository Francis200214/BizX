package com.biz.common.http;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * Request工具类
 *
 * @author francis
 */
public class RequestUtils {

    private static final RequestAttributes REQUEST_ATTRIBUTES = RequestContextHolder.getRequestAttributes();

    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) REQUEST_ATTRIBUTES;
    }

}
