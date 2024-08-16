package com.biz.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * Request工具类，用于获取当前 HTTP 请求的相关信息。
 * <p>该类提供了方便的方法来获取当前线程绑定的 {@link HttpServletRequest} 对象。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     HttpServletRequest request = RequestUtils.getHttpServletRequest();
 *     String clientIp = request.getRemoteAddr();
 * }</pre>
 *
 * <p>该工具类使用了 Spring 提供的 {@link RequestContextHolder} 来获取当前请求上下文。</p>
 *
 * <p>注意：该工具类依赖于 Spring Web 环境，应确保在 Spring 应用上下文中使用。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
@Slf4j
public class RequestUtils {

    /**
     * 获取当前线程绑定的 {@link HttpServletRequest} 对象。
     *
     * @return 当前的 {@link HttpServletRequest} 对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取当前线程绑定的 {@link ServletRequestAttributes} 对象。
     *
     * @return 当前的 {@link ServletRequestAttributes} 对象
     * @throws IllegalStateException 如果当前线程没有绑定 {@link ServletRequestAttributes}
     */
    public static ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No request attributes found for current thread");
        }
        return attributes;
    }
}
