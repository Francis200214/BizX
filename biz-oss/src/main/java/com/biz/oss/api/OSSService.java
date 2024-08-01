package com.biz.oss.api;

/**
 * OSS 服务
 *
 * @author francis
 * @since 2024-06-22 11:48
 **/
public interface OSSService {


    /**
     * 上传文件
     *
     * @param fileName 自定义文件名【如果给Null，则内部生成随机文件名】
     * @param fileType 文件类型后缀
     * @param content 文件字节数组
     * @param contentType contentType
     * @param isPublic 是否是公共文件
     * @return 文件Id
     */
    String put(String fileName, String fileType, byte[] content, String contentType, boolean isPublic);


    /**
     * 删除文件
     *
     * @param id 文件Id
     * @param isPublic 是否是公共文件
     */
    void remote(String id, boolean isPublic);

    /***
     * 获取文件访问路径
     *
     * @param id 文件Id
     * @param isPublic 是否是公共文件
     * @return
     */
    String findUrl(String id, boolean isPublic);


}
