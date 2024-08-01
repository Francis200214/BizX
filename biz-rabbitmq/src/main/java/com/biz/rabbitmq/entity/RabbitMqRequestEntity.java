package com.biz.rabbitmq.entity;

import lombok.*;

import java.io.Serializable;

/**
 * RabbitMQ 请求消息实体
 *
 * @author francis
 * @since 2023-08-18 15:07
 **/
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitMqRequestEntity<T> implements Serializable {

    /**
     * 非必填，后台可自动生成  applicationName_随机日期id
     * 业务id，用于识别集体业务（也是消息应答重试的主要来源）
     */
    private String businessId;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 发送者主题
     */
    public String sendTopic;

    /**
     * 业务描述
     */
    private String description;

    /**
     * 发送消息的_项目名称
     */
    private String sendApplicationName;

}
