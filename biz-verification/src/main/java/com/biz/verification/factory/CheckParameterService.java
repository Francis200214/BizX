package com.biz.verification.factory;

import java.lang.annotation.Annotation;

/**
 * 参数校验服务接口。
 * <p>定义了处理方法参数校验的接口方法。</p>
 *
 * @see Annotation
 * @author francis
 * @version 1.0
 * @since 2023-04-09
 **/
public interface CheckParameterService {

    /**
     * 处理数据，校验 GET 接口中的参数。
     *
     * @param annotation 参数上的注解，不能为空
     * @param args       参数值，不能为空
     * @throws Throwable 如果校验失败或处理过程中出现异常
     */
    void handle(Annotation annotation, Object args) throws Throwable;

}
