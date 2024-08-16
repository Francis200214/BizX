package com.biz.common.file;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 图像压缩工具类
 * <p>基于 Thumbnailator 库封装了图片压缩、格式转换和裁剪等操作。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     ImageCompressor.compressImage("source.jpg", "output.jpg");
 *     ImageCompressor.convertImageFormat("source.jpg", "output.png", "png");
 * }</pre>
 *
 * <p>该类提供了多种方法来处理图像文件，包括压缩、格式转换和裁剪功能。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
@Slf4j
public class ImageCompressor {

    /**
     * 文件默认最大大小 1024KB
     */
    private static final int MAX_SIZE_KB = 1024;

    /**
     * 压缩图片并保持宽高比。
     *
     * @param sourcePath 源图片路径，不能为空
     * @param destPath   目标图片路径，不能为空
     * @param width      目标宽度，必须为正整数
     * @param height     目标高度，必须为正整数
     * @throws IOException 如果压缩过程发生 I/O 错误
     */
    public static void compressImage(String sourcePath, String destPath, int width, int height) throws IOException {
        Thumbnails.of(sourcePath)
                .size(width, height)
                .keepAspectRatio(true)
                .toFile(destPath);
    }

    /**
     * 使用默认最大大小压缩图片。
     *
     * @param sourcePath 源图片路径，不能为空
     * @param destPath   目标图片路径，不能为空
     * @throws IOException 如果压缩过程发生 I/O 错误
     */
    public static void compressImage(String sourcePath, String destPath) throws IOException {
        handle(new File(sourcePath), new File(destPath), MAX_SIZE_KB);
    }

    /**
     * 根据指定的最大大小压缩图片。
     *
     * @param sourcePath 源图片路径，不能为空
     * @param destPath   目标图片路径，不能为空
     * @param maxSizeKB  图片的最大大小（KB），必须为正整数
     * @throws IOException 如果压缩过程发生 I/O 错误
     */
    public static void compressImage(String sourcePath, String destPath, int maxSizeKB) throws IOException {
        handle(new File(sourcePath), new File(destPath), maxSizeKB);
    }

    /**
     * 将图片转换为指定格式。
     *
     * @param sourcePath 源图片路径，不能为空
     * @param destPath   目标图片路径，不能为空
     * @param format     目标格式，如 "jpg", "png"，不能为空
     * @throws IOException 如果转换过程发生 I/O 错误
     */
    public static void convertImageFormat(String sourcePath, String destPath, String format) throws IOException {
        Thumbnails.of(sourcePath)
                .outputFormat(format)
                .toFile(destPath);
    }

    /**
     * 裁剪图片到指定尺寸。
     *
     * @param sourcePath 源图片路径，不能为空
     * @param destPath   目标图片路径，不能为空
     * @param x          裁剪区域左上角x坐标，必须为非负整数
     * @param y          裁剪区域左上角y坐标，必须为非负整数
     * @param width      裁剪区域宽度，必须为正整数
     * @param height     裁剪区域高度，必须为正整数
     * @throws IOException 如果裁剪过程发生 I/O 错误
     */
    public static void cropImage(String sourcePath, String destPath, int x, int y, int width, int height) throws IOException {
        Thumbnails.of(sourcePath)
                .sourceRegion(x, y, width, height)
                .size(width, height)
                .toFile(destPath);
    }

    /**
     * 处理图片压缩，确保图片不超过指定的最大大小。
     *
     * @param inputFile  输入图片文件，不能为空
     * @param outputFile 输出图片文件，不能为空
     * @param maxSizeKB  最大大小（KB），必须为正整数
     * @throws IOException 如果压缩过程发生 I/O 错误
     */
    private static void handle(File inputFile, File outputFile, int maxSizeKB) throws IOException {
        long inputFileSize = Files.size(inputFile.toPath());

        if (inputFileSize <= maxSizeKB * 1024L) {
            Files.copy(inputFile.toPath(), outputFile.toPath());
            return;
        }

        double scale = Math.sqrt((double) (maxSizeKB * 1024) / inputFileSize);

        Thumbnails.of(inputFile)
                .scale(scale)
                .outputQuality(0.85)
                .toFile(outputFile);

        long outputFileSize = Files.size(outputFile.toPath());

        while (outputFileSize > maxSizeKB * 1024L) {
            scale *= 0.9;
            Thumbnails.of(outputFile)
                    .scale(scale)
                    .outputQuality(0.85)
                    .toFile(outputFile);
            outputFileSize = Files.size(outputFile.toPath());
        }
    }

}
