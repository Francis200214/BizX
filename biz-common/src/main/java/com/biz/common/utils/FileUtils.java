package com.biz.common.utils;

import cn.hutool.core.io.FileUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 文件工具类
 *
 * @author francis
 */
public final class FileUtils {

    /**
     * 文件转成base64编码
     *
     * @return
     */
    public static FileDTO fileToBase64(File file) {
        if (!file.exists()) {
            return null;
        }
        // base64编码
        String context = null;
        try {
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            context = Base64.getEncoder().encodeToString(buffer).replaceAll("\r|\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FileDTO.builder()
                .type(file.getName().split("\\.")[1])
                .context(context)
                .build();
    }



    /**
     * base64转成文件并保存
     *
     * @param filePath 文件存储路径
     * @param fileContext 文件base64编码
     * @param fileType 文件类型后缀（jpg、png）
     */
    public static void base64ToFile(String filePath, String fileContext, String fileType) {
        // 判断文件base64编码是否为空
        if (Common.isBlank(fileContext)) {
            return;
        }
        // 设置文件存储位置
        final Base64.Decoder decoder = Base64.getDecoder();
        // 生成文件
        File file = new File(filePath);
        // 创建文件及其父目录【这里使用hutool的方法】
        FileUtil.touch(file);
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(decoder.decode(fileContext));
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    @Setter
    @Getter
    @Builder
    public static class FileDTO {
        // 文件类型
        private String type;
        // 文件base64编码
        private String context;
    }


}
