package com.biz.oss.template;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Amazon S3 操作实现类。
 * <p>该类提供了对 Amazon S3 的常见操作，包括创建和删除桶、上传和下载对象、获取对象的 URL 等。</p>
 *
 * @author francis
 * @version 1.4.11
 * @since 2023-04-21
 */
@RequiredArgsConstructor
public class AmazonS3TemplateImpl implements OssTemplate {

    private final AmazonS3 amazonS3;

    /**
     * 创建 Bucket。
     *
     * @param bucketName Bucket 名称
     * @throws RuntimeException 如果创建 Bucket 失败
     */
    @Override
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }

    /**
     * 获取所有的 Buckets。
     *
     * @return 所有的 Buckets
     */
    @Override
    public List<Bucket> getAllBuckets() {
        return amazonS3.listBuckets();
    }

    /**
     * 检查 Bucket 是否存在。
     *
     * @param bucketName Bucket 名称
     * @return true 如果 Bucket 存在，否则返回 false
     */
    @Override
    public boolean bucketExists(String bucketName) {
        return amazonS3.doesBucketExistV2(bucketName);
    }

    /**
     * 删除 Bucket。
     *
     * @param bucketName Bucket 名称
     * @throws RuntimeException 如果删除 Bucket 失败
     */
    @Override
    public void removeBucket(String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }

    /**
     * 上传对象。
     *
     * @param bucketName  Bucket 名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param contextType 文件类型
     * @throws RuntimeException 如果上传对象失败
     */
    @Override
    public void putObject(String bucketName, String objectName, InputStream stream, String contextType) throws IOException {
        this.putObject(bucketName, objectName, stream, stream.available(), contextType);
    }

    /**
     * 上传对象。
     *
     * @param bucketName Bucket 名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws RuntimeException 如果上传对象失败
     */
    @Override
    public void putObject(String bucketName, String objectName, InputStream stream) throws IOException {
        this.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    /**
     * 获取对象。
     *
     * @param bucketName Bucket 名称
     * @param objectName 文件名称
     * @return S3 对象
     * @throws RuntimeException 如果获取对象失败
     */
    @Override
    public S3Object getObject(String bucketName, String objectName) {
        return amazonS3.getObject(bucketName, objectName);
    }

    /**
     * 获取对象的 URL。
     *
     * @param bucketName Bucket 名称
     * @param objectName 文件名称
     * @param expires    URL 过期时间（天）
     * @return 对象的 URL
     * @throws RuntimeException 如果获取对象 URL 失败
     */
    @Override
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, expires);
        URL url = amazonS3.generatePresignedUrl(bucketName, objectName, calendar.getTime());
        return url.toString();
    }

    /**
     * 获取对象的 URL。
     *
     * @param bucketName Bucket 名称
     * @param objectName 文件名称
     * @param expires    URL 过期时间
     * @param timeUnit   时间单位
     * @return 对象的 URL
     * @throws RuntimeException 如果获取对象 URL 失败
     */
    @Override
    public String getObjectURL(String bucketName, String objectName, Integer expires, TimeUnit timeUnit) {
        if (timeUnit != null) {
            return getObjectURL(bucketName, objectName, (int) timeUnit.toDays(expires));
        }
        return "";
    }

    /**
     * 删除对象。
     *
     * @param bucketName Bucket 名称
     * @param objectName 文件名称
     * @throws RuntimeException 如果删除对象失败
     */
    @Override
    public void removeObject(String bucketName, String objectName) {
        amazonS3.deleteObject(bucketName, objectName);
    }

    /**
     * 根据前缀获取对象集合。
     *
     * @param bucketName Bucket 名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return 对象集合
     * @throws RuntimeException 如果获取对象集合失败
     */
    @Override
    public List<S3ObjectSummary> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName, prefix);
        return objectListing.getObjectSummaries();
    }

    /**
     * 上传文件。
     *
     * @param bucketName  Bucket 名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        文件大小
     * @param contextType 文件类型
     * @return 上传结果
     * @throws RuntimeException 如果上传文件失败
     */
    private PutObjectResult putObject(String bucketName, String objectName, InputStream stream, long size,
                                      String contextType) throws IOException {
        byte[] bytes = IOUtils.toByteArray(stream);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contextType);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 上传
        return amazonS3.putObject(bucketName, objectName, byteArrayInputStream, objectMetadata);
    }
}
