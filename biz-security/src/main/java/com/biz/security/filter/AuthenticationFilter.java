package com.biz.security.filter;

import com.biz.security.authentication.AuthenticationFactory;
import com.biz.security.authentication.AuthenticationService;
import com.biz.security.authentication.LoginRequest;
import com.biz.security.authentication.type.AuthType;
import com.biz.security.authentication.type.AuthTypeBuilder;
import com.biz.security.error.AuthenticationTypeConvertException;
import com.biz.security.filter.chain.FilterChain;
import com.biz.security.filter.constant.HttpConstant;
import com.biz.security.user.UserDetails;
import com.biz.security.user.store.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证校验过滤器。
 *
 * <p>
 * 用于校验用户的认证信息。
 * </p>
 *
 * @author francis
 * @version 1.0.1
 * @since 2024-10-09
 */
@Slf4j
public class AuthenticationFilter implements SecurityFilter {

    /**
     * 认证服务工厂。
     */
    private final AuthenticationFactory authenticationFactory;

    /**
     * 用户信息存储器。
     */
    private final SecurityContextHolder securityContextHolder;

    /**
     * 构造方法。
     *
     * @param authenticationFactory 认证服务工厂
     */
    public AuthenticationFilter(AuthenticationFactory authenticationFactory, SecurityContextHolder securityContextHolder) {
        this.authenticationFactory = authenticationFactory;
        this.securityContextHolder = securityContextHolder;
    }

    /**
     * 执行认证过滤。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    过滤器链
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        if (log.isDebugEnabled()) {
            log.debug("AuthenticationFilter 开始执行认证过滤");
        }
        try {
            // 获取认证类型
            AuthType authType = AuthTypeBuilder.getAuthType(request.getHeader(HttpConstant.AUTH_TYPE));
            if (authType == null) {
                // 未识别的认证类型, 执行下一个过滤器
                chain.doFilter(request, response);
                return;
            }

            // 根据认证类型获取认证服务
            AuthenticationService authenticationService = authenticationFactory.getAuthenticationService(authType);
            // 获取用户信息
            UserDetails userDetails = this.getUserDetails(request, authType, authenticationService);
            // 设置用户信息
            this.setUserDetails(userDetails);

        } catch (AuthenticationTypeConvertException e) {
            if (log.isDebugEnabled()) {
                log.debug("未知的认证类型异常", e);
            }
            try {
                response.sendError(e.getCode(), e.getMessage());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("认证失败", e);
            }
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return;
        }

        // 认证通过，执行下一个过滤器
        chain.doFilter(request, response);
    }

    /**
     * 设置用户信息。
     *
     * @param userDetails 用户信息
     */
    private void setUserDetails(UserDetails userDetails) {
        securityContextHolder.setContext(userDetails);
    }

    /**
     * 获取用户信息。
     *
     * @param request               请求对象
     * @param authType              认证类型
     * @param authenticationService 认证服务
     * @return 用户信息
     */
    private UserDetails getUserDetails(HttpServletRequest request, AuthType authType, AuthenticationService authenticationService) {
        switch (authType) {
            case USERNAME_PASSWORD:
                return this.getUserDetailsByUsernamePassword(request, authenticationService);
            case TOKEN:
                return this.getUserDetailsByToken(request, authenticationService);
            case OAUTH2:
                return this.getUserDetailsByOAuth2(request, authenticationService);
        }
        return null;
    }

    /**
     * 根据 OAuth2 获取用户信息。
     *
     * @param request               请求对象
     * @param authenticationService 认证服务
     * @return 用户信息
     */
    private UserDetails getUserDetailsByOAuth2(HttpServletRequest request, AuthenticationService authenticationService) {
        return authenticationService.authenticate(
                LoginRequest.oAuth2Authentication(
                        this.getOauth2Provider(request), this.getOauth2Token(request)));
    }

    /**
     * 获取 OAuth2 认证信息。
     *
     * @param request 请求对象
     * @return OAuth2 认证信息
     */
    private String getOauth2Provider(HttpServletRequest request) {
        return request.getHeader(OAuth2ParameterConstant.OAUTH2_PROVIDER);
    }

    /**
     * 获取 OAuth2 认证 Token。
     *
     * @param request 请求对象
     * @return OAuth2 认证 Token
     */
    private String getOauth2Token(HttpServletRequest request) {
        return request.getHeader(OAuth2ParameterConstant.OAUTH2_TOKEN);
    }

    /**
     * 根据 Token 获取用户信息。
     *
     * @param request               请求对象
     * @param authenticationService 认证服务
     * @return 用户信息
     */
    private UserDetails getUserDetailsByToken(HttpServletRequest request, AuthenticationService authenticationService) {
        return authenticationService.authenticate(LoginRequest.tokenAuthentication(request.getHeader(TokenParameterConstant.TOKEN)));
    }

    /**
     * 根据用户名密码获取用户信息。
     *
     * @param request               请求对象
     * @param authenticationService 认证服务
     * @return 用户信息
     */
    private UserDetails getUserDetailsByUsernamePassword(HttpServletRequest request, AuthenticationService authenticationService) {
        if (!request.getMethod().equals(HttpConstant.POST)) {
            throw new IllegalArgumentException("请求方式必须为 POST");
        }

        return authenticationService.authenticate(
                LoginRequest.userNameAndPasswordAuthentication(
                        this.getUsername(request), this.getPassword(request)));
    }

    /**
     * 获取用户名。
     *
     * @param request 请求对象
     * @return 用户名
     */
    private String getUsername(HttpServletRequest request) {
        return request.getParameter(UsernamePasswordParameterConstant.USERNAME);
    }

    /**
     * 获取密码。
     *
     * @param request 请求对象
     * @return 密码
     */
    private String getPassword(HttpServletRequest request) {
        return request.getParameter(UsernamePasswordParameterConstant.PASSWORD);
    }

    /**
     * 用户名密码参数常量类。
     */
    private static class UsernamePasswordParameterConstant {
        /**
         * 用户名参数名。
         */
        private static final String USERNAME = "username";
        /**
         * 密码参数名。
         */
        private static final String PASSWORD = "password";
    }

    /**
     * Token 参数常量类。
     */
    private static class TokenParameterConstant {
        /**
         * Token 参数名。
         */
        private static final String TOKEN = "X-Token";
    }

    /**
     * OAuth2 参数常量类。
     */
    private static class OAuth2ParameterConstant {
        /**
         * OAuth2 认证信息参数名。
         */
        private static final String OAUTH2_PROVIDER = "X-OAuth2-Provider";
        /**
         * OAuth2 认证信息参数名。
         */
        private static final String OAUTH2_TOKEN = "X-OAuth2-Token";
    }
}
