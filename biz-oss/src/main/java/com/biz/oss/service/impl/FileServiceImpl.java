package com.biz.oss.service.impl;

import com.biz.oss.service.FileService;
import com.biz.oss.template.OssTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 文件服务实现类，提供文件上传、获取访问路径和删除文件的方法。
 *
 * <p>该类实现了 {@link FileService} 接口，并使用 {@link OssTemplate} 进行具体的 OSS 操作。</p>
 *
 * @see FileService
 * @see OssTemplate
 *
 * @author francis
 * @version 1.4.11
 * @since 1.0.1
 */
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final OssTemplate ossTemplate;

    /**
     * 上传文件到指定的存储桶。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param input 文件流，不能为空
     * @param objectName 对象名称，不能为空
     * @param contentType 文件类型，不能为空
     */
    @Override
    public void uploadFile(String bucketName, InputStream input, String objectName, String contentType) {
        try {
            ossTemplate.putObject(bucketName, objectName, input, contentType);
        } catch (Exception e) {
            log.error("上传文件失败", e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                log.error("关闭流异常", e);
            }
        }
    }

    /**
     * 根据对象名称获取公共文件访问路径。
     * <p>该文件访问路径默认可访问一天。</p>
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @return 文件访问路径
     */
    @Override
    public String getUrlByBucketNameAndObjectName(String bucketName, String objectName) {
        return bucketName + "/" + objectName;
    }

    /**
     * 根据对象名称获取文件访问路径。
     *
     * @param bucketName 存储桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @param getUrlTime 访问文件路径时间，不能为空
     * @param timeUnit 时间单位，不能为空
     * @return 文件访问路径
     */
    @Override
    public String getUrlByBucketNameAndObjectNameAndTime(String bucketName, String objectName, int getUrlTime, TimeUnit timeUnit) {
        return this.getUrl(bucketName, objectName, getUrlTime, timeUnit);
    }

    /**
     * 根据对象名称和桶名称删除对象。
     *
     * @param bucketName 桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @throws Exception 如果删除对象失败
     */
    @Override
    public void remove(String bucketName, String objectName) throws Exception {
        ossTemplate.removeObject(bucketName, objectName);
    }

    /**
     * 根据桶名称、对象名称和时间获取文件路径。
     *
     * @param bucketName 桶名称，不能为空
     * @param objectName 对象名称，不能为空
     * @param getUrlTime 访问文件路径时间，不能为空
     * @param timeUnit 时间单位，不能为空
     * @return 文件路径
     */
    private String getUrl(String bucketName, String objectName, int getUrlTime, TimeUnit timeUnit) {
        String url = ossTemplate.getObjectURL(bucketName, objectName, getUrlTime, timeUnit);
        try {
            return getPath(url);
        } catch (Exception e) {
            log.error("获取文件路径失败", e);
        }
        return null;
    }

    /**
     * 去除 URL 中的 "scheme://host:port/" 部分。
     *
     * @param url URL
     * @return 去除 scheme 和 host 部分后的 URL
     */
    private static String getPath(String url) {
        if (url == null) {
            return null;
        }
        char[] chars = url.toCharArray();
        int start = 0, find = 0, len = chars.length;
        for (; start < len; start++) {
            if (chars[start] == '/') {
                find++;
                if (find == 3) {
                    return new String(chars, start + 1, len - start - 1);
                }
            }
        }
        return url;
    }
}
