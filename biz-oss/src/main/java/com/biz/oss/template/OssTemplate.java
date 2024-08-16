package com.biz.oss.template;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OSS 操作模板接口，定义了对 OSS 进行操作的方法。
 * <p>该接口提供了创建和删除桶、上传和下载对象、获取对象 URL 等常见操作。</p>
 *
 * @see AmazonS3TemplateImpl
 * @see Bucket
 * @see S3Object
 * @see S3ObjectSummary
 *
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
public interface OssTemplate {

    /**
     * 创建 Bucket。
     *
     * @param bucketName Bucket 名称，不能为空
     */
    void createBucket(String bucketName);

    /**
     * 获取所有的 Buckets。
     *
     * @return 所有的 Buckets 列表
     */
    List<Bucket> getAllBuckets();

    /**
     * 判断 Bucket 是否存在。
     *
     * @param bucketName Bucket 名称，不能为空
     * @return true 如果 Bucket 存在，否则返回 false
     */
    boolean bucketExists(String bucketName);

    /**
     * 删除 Bucket。
     *
     * @param bucketName Bucket 名称，不能为空
     */
    void removeBucket(String bucketName);

    /**
     * 上传文件到指定的存储桶。
     *
     * @param bucketName  存储桶名称，不能为空
     * @param objectName  文件名称，不能为空
     * @param stream      文件流，不能为空
     * @param contextType 文件类型，不能为空
     * @throws Exception 如果上传文件失败
     */
    void putObject(String bucketName, String objectName, InputStream stream, String contextType) throws Exception;

    /**
     * 上传文件到指定的存储桶。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 文件名称，不能为空
     * @param stream     文件流，不能为空
     * @throws Exception 如果上传文件失败
     */
    void putObject(String bucketName, String objectName, InputStream stream) throws Exception;

    /**
     * 获取文件。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 文件名称，不能为空
     * @return S3 对象
     */
    S3Object getObject(String bucketName, String objectName);

    /**
     * 获取对象的 URL。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 文件名称，不能为空
     * @param expires    URL 过期时间（天）
     * @return 对象的 URL
     */
    String getObjectURL(String bucketName, String objectName, Integer expires);

    /**
     * 获取对象的 URL。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 文件名称，不能为空
     * @param expires    URL 过期时间
     * @param timeUnit   时间单位
     * @return 对象的 URL
     */
    String getObjectURL(String bucketName, String objectName, Integer expires, TimeUnit timeUnit);

    /**
     * 删除对象。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 文件名称，不能为空
     * @throws Exception 如果删除对象失败
     */
    void removeObject(String bucketName, String objectName) throws Exception;

    /**
     * 根据前缀获取对象集合。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param prefix     对象前缀
     * @param recursive  是否递归查询
     * @return 对象集合
     */
    List<S3ObjectSummary> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive);

}
