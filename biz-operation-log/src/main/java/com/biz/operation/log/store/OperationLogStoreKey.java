package com.biz.operation.log.store;

import lombok.*;

import java.io.Serializable;

/**
 * 操作日志存储 key。
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
public class OperationLogStoreKey implements Serializable {

    /**
     * 当前操作日志产生的类名。
     */
    private String className;

    /**
     * 当前操作日志产生的方法名。
     */
    private String methodName;

}
