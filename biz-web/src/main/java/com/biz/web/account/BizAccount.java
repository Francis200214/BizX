package com.biz.web.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

/**
 * 当前的用户信息
 *
 * @author francis
 */
public interface BizAccount<ID extends Serializable> {

    /**
     * 用户Id
     *
     * @return
     */
    ID getId();

    /**
     * 用户的角色
     *
     * @return
     */
    Set<String> roles();

    /**
     * 会话信息
     *
     * @author francis
     * @create: 2023-04-18 10:09
     **/
    @Setter
    @Getter
    @EqualsAndHashCode
    class BizSession implements Serializable {

        /**
         * 当前用户信息
         */
        private BizAccount<?> userinfo;

        /**
         * 创建时间
         */
        private long createTime;

        /**
         * 时间到期后清除任务
         */
        private ScheduledFuture<?> scheduledFuture;

    }
}
