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
 * 文件工具类，提供文件与 Base64 编码之间的转换、文件创建等功能。
 * <p>该类包含了一些静态方法，用于处理文件操作的常见任务，例如将文件转换为 Base64 编码字符串，或将 Base64 编码字符串保存为文件。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     File file = new File("example.txt");
 *     FileUtils.FileDTO fileDTO = FileUtils.fileToBase64(file);
 *     FileUtils.base64ToFile("output.txt", fileDTO.getContext(), fileDTO.getType());
 * }</pre>
 *
 * <p>注意：该工具类不可实例化。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
@Slf4j
public final class FileUtils {

    /**
     * 将文件转换为 Base64 编码字符串。
     *
     * @param file 要转换的文件，不能为空
     * @return 包含文件类型和 Base64 编码内容的 {@link FileDTO} 对象，如果文件不存在则返回 null
     */
    public static FileDTO fileToBase64(File file) {
        if (!file.exists()) {
            return null;
        }
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
     * 将 Base64 编码字符串转换为文件并保存到指定路径。
     *
     * @param filePath    文件存储路径，不能为空
     * @param fileContext 文件的 Base64 编码字符串，不能为空
     * @param fileType    文件类型后缀（如 "jpg"、"png"），不能为空
     */
    public static void base64ToFile(String filePath, String fileContext, String fileType) {
        if (Common.isBlank(fileContext)) {
            return;
        }
        final Base64.Decoder decoder = Base64.getDecoder();
        File file = new File(filePath);
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
     * 根据给定的 {@link File} 对象创建目录和文件。
     * 如果目录和文件已经存在则不进行创建操作。
     *
     * @param file 要创建的文件，不能为空
     * @return true 如果文件成功创建或已经存在，false 如果创建失败
     * @throws IllegalArgumentException 如果文件对象为 null
     */
    public static boolean createFileIfNotExists(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File must not be null");
        }

        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    System.err.println("Failed to create directory: " + parentDir);
                    return false;
                }
            }

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

    /**
     * 数据传输对象，用于保存文件类型和 Base64 编码内容。
     */
    @Setter
    @Getter
    @Builder
    public static class FileDTO {
        /**
         * 文件类型（如 "jpg"、"png"）。
         */
        private String type;

        /**
         * 文件的 Base64 编码内容。
         */
        private String context;
    }
}
