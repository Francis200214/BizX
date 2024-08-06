package com.biz.oss.utils;

import com.biz.common.date.datetime.DateTimeUtils;
import com.biz.common.id.UUIDGenerate;
import com.biz.oss.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * OSS 服务工具类，提供文件上传、删除和获取访问路径的操作。
 * <p>该类依赖于 {@link FileService} 进行具体的 OSS 操作。</p>
 *
 * @see FileService
 * @see DateTimeUtils
 * @see UUIDGenerate
 *
 * @author francis
 * @version 1.4.11
 * @since 2024-06-22
 */
@Slf4j
@RequiredArgsConstructor
public class OSSUtils {

    private final FileService fileService;

    private static final String PUBLIC_BUCKET_NAME = "public";
    private static final String PRIVATE_BUCKET_NAME = "private";
    private static final String DATE_TIME = "yyyyMMdd";

    /**
     * 上传文件到指定的存储桶。
     *
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param content 文件内容
     * @param contentType 文件的内容类型
     * @param isPublic 是否为公共访问
     * @return 上传后的对象名称
     */
    public String put(String fileName, String fileType, byte[] content, String contentType, boolean isPublic) {
        String objectName = createObjectName(fileName, fileType);
        fileService.uploadFile(
                isPublic ? PUBLIC_BUCKET_NAME : PRIVATE_BUCKET_NAME,
                new ByteArrayInputStream(content),
                objectName,
                contentType);
        return objectName;
    }

    /**
     * 删除指定的文件。
     *
     * @param id 文件ID
     * @param isPublic 是否为公共访问
     * @throws Exception 如果删除文件失败
     */
    public void delete(String id, boolean isPublic) throws Exception {
        fileService.remove(isPublic ? PUBLIC_BUCKET_NAME : PRIVATE_BUCKET_NAME, id);
    }

    /**
     * 获取文件的访问路径。
     *
     * @param id 文件ID
     * @param isPublic 是否为公共访问
     * @return 文件访问路径
     */
    public String findUrl(String id, boolean isPublic) {
        return isPublic ? fileService.getUrlByBucketNameAndObjectName(PUBLIC_BUCKET_NAME, id)
                : fileService.getUrlByBucketNameAndObjectNameAndTime(PRIVATE_BUCKET_NAME, id, 5, TimeUnit.MINUTES);
    }

    /**
     * 创建对象名称。
     *
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return 创建的对象名称
     */
    private static String createObjectName(String fileName, String fileType) {
        return DateTimeUtils.longToDateStr(System.currentTimeMillis(), DATE_TIME) + "/"
                + UUIDGenerate.generate() + "/"
                + Optional.ofNullable(fileName).orElse(UUIDGenerate.generate())
                + fileType;
    }
}
