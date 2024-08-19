package com.biz.rabbitmq.entity;

import lombok.*;

import java.io.Serializable;

/**
 * AMQP 请求消息实体类。
 * <p>
 * 该类表示一个AMQP请求消息，包含业务ID、业务数据、发送者主题、业务描述以及发送应用程序名称等信息。
 * </p>
 *
 * @param <T> 业务数据的类型
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AMQPRequestEntity<T> implements Serializable {

    /**
     * 业务ID，用于识别具体业务（也是消息应答重试的主要依据）。
     * 非必填，后台可自动生成格式为 applicationName_随机日期id 的值。
     */
    private String businessId;

    /**
     * 业务数据。
     */
    private T data;

    /**
     * 发送者主题。
     */
    public String sendTopic;

    /**
     * 业务描述。
     */
    private String description;

    /**
     * 发送消息的项目名称。
     */
    private String sendApplicationName;
}
