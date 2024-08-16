package com.biz.oss.service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 文件服务接口，定义了文件上传、获取访问路径和删除文件的方法。
 * <p>该接口提供了与文件存储服务交互的基本操作。</p>
 *
 * <p>注意：确保实现此接口的类提供必要的异常处理和日志记录。</p>
 *
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
public interface FileService {

    /**
     * 上传文件到指定的存储桶。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param input 文件流，不能为空
     * @param objectName 对象名称，不能为空
     * @param contentType 文件类型，不能为空
     * @throws IllegalArgumentException 如果参数为空或无效
     */
    void uploadFile(String bucketName, InputStream input, String objectName, String contentType);

    /**
     * 根据对象名称获取公共文件访问路径。
     * <p>该文件访问路径默认可访问一天。</p>
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @return 文件访问路径
     * @throws IllegalArgumentException 如果参数为空或无效
     */
    String getUrlByBucketNameAndObjectName(String bucketName, String objectName);

    /**
     * 根据对象名称获取文件访问路径。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @param getUrlTime 访问文件路径时间，不能为空
     * @param timeUnit 时间单位，不能为空
     * @return 文件访问路径
     * @throws IllegalArgumentException 如果参数为空或无效
     */
    String getUrlByBucketNameAndObjectNameAndTime(String bucketName, String objectName, int getUrlTime, TimeUnit timeUnit);

    /**
     * 根据对象名称和桶名称删除对象。
     *
     * @param bucketName 桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @throws Exception 如果删除对象失败
     * @throws IllegalArgumentException 如果参数为空或无效
     */
    void remove(String bucketName, String objectName) throws Exception;

}
