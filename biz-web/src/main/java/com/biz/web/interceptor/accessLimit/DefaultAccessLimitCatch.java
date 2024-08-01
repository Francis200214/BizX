package com.biz.web.interceptor.accessLimit;

import com.biz.cache.map.SingletonScheduledMap;
import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认接口访问次数缓存
 *
 * @author francis
 * @since 2023-06-07 16:51
 **/

public class DefaultAccessLimitCatch extends AbstractAccessLimitCatch {

    /**
     * 缓存10秒
     */
    private final SingletonScheduledMap<InvokeKey, AtomicInteger> accessLimitCatchMap = SingletonScheduledMap.<InvokeKey, AtomicInteger>builder()
            .died(1000 * 10L)
            .build();

    @Override
    protected AtomicInteger get(String addr, String requestUrl) {
        return accessLimitCatchMap.get(createInvokeKey(addr, requestUrl));
    }


    private static InvokeKey createInvokeKey(String addr, String requestUrl) {
        return InvokeKey.builder()
                .addr(addr)
                .requestUrl(requestUrl)
                .build();
    }


    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @Builder
    private static class InvokeKey {
        private String addr;
        private String requestUrl;
    }


}
