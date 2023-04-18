package com.biz.web.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * 会话信息
 *
 * @author francis
 * @create: 2023-04-18 10:09
 **/
@Setter
@Getter
@EqualsAndHashCode
public class BizSession implements Serializable {

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