package com.biz.oss.api.impl;

import com.biz.common.date.DateConstant;
import com.biz.common.id.UUIDGenerate;
import com.biz.common.utils.Common;
import com.biz.oss.api.OSSService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * OSS 服务实现
 *
 * @author francis
 * @since 2024-06-22 11:48
 **/
@RequiredArgsConstructor
public class OSSServiceImpl implements OSSService {

    @Override
    public String put(String fileName, String fileType, byte[] content, String contentType, boolean isPublic) {
        return "";
    }

    @Override
    public void remote(String id, boolean isPublic) {

    }

    @Override
    public String findUrl(String id, boolean isPublic) {
        return "";
    }

    private static String createName(String fileName, String fileType) {
        return Common.now(DateConstant.DEFAULT_YYYY_MM_DD) + "/"
                + UUIDGenerate.generate() + "/"
                + Optional.ofNullable(fileName).orElse(UUIDGenerate.generate())
                + fileType;
    }

}
