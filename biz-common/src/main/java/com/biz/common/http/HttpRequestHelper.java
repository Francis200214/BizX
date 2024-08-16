package com.biz.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Http Request 工具类，提供创建和执行HTTP请求的能力。
 * <p>该类封装了 {@link RestTemplate}，并提供了方便的链式API来创建和执行各种HTTP请求（GET、POST、PUT、DELETE）。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     String response = HttpRequestHelper.post("http://example.com/api")
 *         .contentType("application/json")
 *         .body("{\"key\":\"value\"}")
 *         .header("Authorization", "Bearer token")
 *         .build()
 *         .execute();
 * }</pre>
 *
 * <p>该类通过静态方法提供了多种HTTP请求方法的Builder实例，并允许设置请求头、表单参数和请求体。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
@Slf4j
public final class HttpRequestHelper {

    private final String uri;
    private final HttpMethod method;
    private HttpHeaders headers;
    private Map<String, Object> formParams;
    private Object body;

    /**
     * 私有构造器，防止直接实例化。
     *
     * @param uri 请求的URI，不能为空
     * @param method HTTP请求方法，不能为空
     */
    private HttpRequestHelper(String uri, HttpMethod method) {
        this.uri = uri;
        this.method = method;
        this.headers = new HttpHeaders();
        this.formParams = new HashMap<>();
    }

    /**
     * 创建一个GET请求的Builder实例。
     *
     * @param uri 请求的URI，不能为空
     * @return Builder实例
     */
    public static Builder get(String uri) {
        return new Builder(uri, HttpMethod.GET);
    }

    /**
     * 创建一个POST请求的Builder实例。
     *
     * @param uri 请求的URI，不能为空
     * @return Builder实例
     */
    public static Builder post(String uri) {
        return new Builder(uri, HttpMethod.POST);
    }

    /**
     * 创建一个PUT请求的Builder实例。
     *
     * @param uri 请求的URI，不能为空
     * @return Builder实例
     */
    public static Builder put(String uri) {
        return new Builder(uri, HttpMethod.PUT);
    }

    /**
     * 创建一个DELETE请求的Builder实例。
     *
     * @param uri 请求的URI，不能为空
     * @return Builder实例
     */
    public static Builder delete(String uri) {
        return new Builder(uri, HttpMethod.DELETE);
    }

    /**
     * 执行HTTP请求，并返回响应体。
     *
     * @return 响应体的字符串内容
     * @throws HttpRequestException 如果请求执行失败，抛出此异常
     */
    public String execute() throws HttpRequestException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity;

        if (!formParams.isEmpty() && headers.getContentType() == MediaType.APPLICATION_FORM_URLENCODED) {
            entity = new HttpEntity<>(formParams, headers);
        } else {
            entity = new HttpEntity<>(body, headers);
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, method, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while sending request: ", e);
            throw new HttpRequestException("Failed to execute HTTP request", e);
        }
    }

    /**
     * Builder类用于构建HttpRequestHelper实例，提供链式调用设置请求属性的方法。
     */
    public static class Builder {
        private final String uri;
        private final HttpMethod method;
        private HttpHeaders headers;
        private Map<String, Object> formParams;
        private Object body;

        /**
         * 构造函数初始化Builder实例。
         *
         * @param uri 请求的URI，不能为空
         * @param method HTTP请求方法，不能为空
         */
        private Builder(String uri, HttpMethod method) {
            this.uri = uri;
            this.method = method;
            this.headers = new HttpHeaders();
            this.formParams = new HashMap<>();
        }

        /**
         * 设置请求的Content-Type。
         *
         * @param contentType Content-Type字符串，不能为空
         * @return Builder实例
         */
        public Builder contentType(String contentType) {
            headers.setContentType(MediaType.parseMediaType(contentType));
            return this;
        }

        /**
         * 设置一个请求头。
         *
         * @param name 头部的名称，不能为空
         * @param value 头部的值，不能为空
         * @return Builder实例
         */
        public Builder header(String name, String value) {
            headers.set(name, value);
            return this;
        }

        /**
         * 添加一个表单参数。
         *
         * @param name 参数的名称，不能为空
         * @param value 参数的值，不能为空
         * @return Builder实例
         */
        public Builder form(String name, Object value) {
            formParams.put(name, value);
            return this;
        }

        /**
         * 设置请求的主体。
         *
         * @param body 请求的主体对象，不能为空
         * @return Builder实例
         */
        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        /**
         * 构建HttpRequestHelper实例。
         *
         * @return HttpRequestHelper实例
         */
        public HttpRequestHelper build() {
            HttpRequestHelper helper = new HttpRequestHelper(uri, method);
            helper.headers = headers;
            helper.formParams = formParams;
            helper.body = body;
            return helper;
        }
    }

    /**
     * HttpRequestException用于表示HTTP请求过程中发生的异常。
     */
    private static class HttpRequestException extends Exception {
        /**
         * 构造函数初始化异常实例。
         *
         * @param message 异常信息
         * @param cause 异常的根原因
         */
        public HttpRequestException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
