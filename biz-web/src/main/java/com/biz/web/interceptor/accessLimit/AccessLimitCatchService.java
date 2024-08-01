package com.biz.web.interceptor.accessLimit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 获取访问限制缓存
 *
 * @author francis
 * @since 2023-06-07 16:40
 **/
public interface AccessLimitCatchService {

    /**
     * 获取调用者X秒内调用的次数
     *
     * @param addr 调用者IP
     * @param requestUrl 接口地址
     * @return X秒内调用N次数（X代表用户设置的时间阈值，N代表X秒内调用不能超过的次数，默认是10秒内不能调用10次）
     */
    AtomicInteger getAccessNumber(String addr, String requestUrl);

}
