package com.biz.web.interceptor.accessLimit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接口访问限制抽象类
 *
 * @author francis
 * @since 2023-06-07 16:49
 **/
public abstract class AbstractAccessLimitCatch implements AccessLimitCatchService {

    @Override
    public AtomicInteger getAccessNumber(String addr, String requestUrl) {
        return get(addr, requestUrl);
    }


    protected abstract AtomicInteger get(String addr, String requestUrl);


}
