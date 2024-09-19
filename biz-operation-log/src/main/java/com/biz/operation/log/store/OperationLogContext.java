package com.biz.operation.log.store;

import lombok.*;

import java.io.Serializable;

/**
 * 日志内容。
 *
 * @author francis
 * @create 2024-09-19
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OperationLogContext implements Serializable {

    /**
     * 操作日志内容。
     */
    private String content;


}
