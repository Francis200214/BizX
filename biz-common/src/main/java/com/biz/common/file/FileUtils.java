package com.biz.common.file;

import com.biz.common.utils.Common;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

/**
 * 文件工具类
 *
 * @author francis
 */
@Slf4j
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
            if (log.isDebugEnabled()) {
                log.error("fileToBase64 error ", e);
            }
        }

        return FileDTO.builder()
                .type(file.getName().split("\\.")[1])
                .context(context)
                .build();
    }


    /**
     * base64转成文件并保存
     *
     * @param filePath    文件存储路径
     * @param fileContext 文件base64编码
     * @param fileType    文件类型后缀（jpg、png）
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
        // 创建文件及其父目录
        createFileIfNotExists(file);
        try (OutputStream out = Files.newOutputStream(file.toPath())) {
            out.write(decoder.decode(fileContext));
            out.flush();

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("base64ToFile error ", e);
            }
        }
    }


    /**
     * 根据给定的File对象创建目录和文件。
     * 如果目录和文件已经存在则不进行创建操作。
     *
     * @param file 要创建的文件
     * @return true 如果文件成功创建或已经存在，false 如果创建失败
     */
    public static boolean createFileIfNotExists(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null");
        }

        try {
            // 检查父目录是否存在，如果不存在则创建
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    System.err.println("Failed to create directory: " + parentDir);
                    return false;
                }
            }

            // 检查文件是否存在，如果不存在则创建
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.err.println("Failed to create file: " + file);
                    return false;
                }
            }

            return true;
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.error("createFileIfNotExists error ", e);
            }
            return false;
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
