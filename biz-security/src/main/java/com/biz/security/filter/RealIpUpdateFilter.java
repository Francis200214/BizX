package com.biz.security.filter;

import com.biz.security.filter.chain.FilterChain;
import com.biz.security.user.UserDetails;
import com.biz.security.user.store.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求真实 IP 地址更新过滤器。
 *
 * <p>
 * 用于在认证通过后获取用户的真实 IP 地址并更新用户上下文。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-10
 */
@Slf4j
public class RealIpUpdateFilter implements SecurityFilter {

    /**
     * 存储用户信息。
     */
    private final SecurityContextHolder securityContextHolder;

    /**
     * 未知 IP 的标识符。
     */
    private static final String UNKNOWN = "unknown";

    /**
     * X-Forwarded-For 请求头。
     */
    private static final String FORWARDED_FOR = "X-Forwarded-For";

    /**
     * X-Real-IP 请求头。
     */
    private static final String REAL_IP = "X-Real-IP";

    /**
     * 构造函数。
     *
     * @param securityContextHolder 存储用户信息实例
     */
    public RealIpUpdateFilter(SecurityContextHolder securityContextHolder) {
        this.securityContextHolder = securityContextHolder;
    }

    /**
     * 执行过滤操作。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        log.debug("执行真实IP过滤器...");
        UserDetails userDetails = securityContextHolder.getContext();
        // 获取客户端 IP
        String ipAddress = getClientIp(request);
        // 设置客户端 IP
        userDetails.setIpAddress(ipAddress);
        // 更新用户信息
        securityContextHolder.refreshContext(userDetails);

        // 处理成功，执行下一个过滤器
        chain.doFilter(request, response);
    }

    /**
     * 获取客户端 IP 地址。
     *
     * @param request 请求对象
     * @return 客户端 IP 地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader(FORWARDED_FOR);
        if (ipAddress != null && !ipAddress.isEmpty() && !UNKNOWN.equalsIgnoreCase(ipAddress)) {
            // X-Forwarded-For 可能包含多个 IP，以逗号分隔，取第一个非 unknown 的 IP 即为客户端真实 IP
            return ipAddress.split(",")[0].trim();
        }
        ipAddress = request.getHeader(REAL_IP);
        if (ipAddress != null && !ipAddress.isEmpty() && !UNKNOWN.equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }
        return request.getRemoteAddr();
    }
}
