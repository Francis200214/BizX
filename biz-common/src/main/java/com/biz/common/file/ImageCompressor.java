package com.biz.common.file;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 图像压缩
 * 基于 thumbnailator 技术进行Api封装的工具类
 *
 * @author francis
 * @create 2024-05-29 19:40
 **/
@Slf4j
public class ImageCompressor {

    /**
     * 文件默认最大大小 1024KB
     */
    private static final int MAX_SIZE_KB = 1024;

    /**
     * 压缩图片并保持宽高比
     *
     * @param sourcePath 源图片路径
     * @param destPath   目标图片路径
     * @param width      目标宽度
     * @param height     目标高度
     */
    public static void compressImage(String sourcePath, String destPath, int width, int height) throws IOException {
        Thumbnails.of(sourcePath)
                .size(width, height)
                .keepAspectRatio(true)
                .toFile(destPath);
    }

    /**
     * 压缩图片
     *
     * @param sourcePath 源图片路径
     * @param destPath   目标图片路径
     */
    public static void compressImage(String sourcePath, String destPath) throws IOException {
        handle(new File(sourcePath), new File(destPath), MAX_SIZE_KB);
    }

    /**
     * 压缩图片
     *
     * @param sourcePath 源图片路径
     * @param destPath   目标图片路径
     */
    public static void compressImage(String sourcePath, String destPath, int maxSizeKB) throws IOException {
        handle(new File(sourcePath), new File(destPath), maxSizeKB);
    }

    /**
     * 将图片转换为指定格式
     *
     * @param sourcePath 源图片路径
     * @param destPath   目标图片路径
     * @param format     目标格式，如"jpg", "png"
     */
    public static void convertImageFormat(String sourcePath, String destPath, String format) throws IOException {
        Thumbnails.of(sourcePath)
                .outputFormat(format)
                .toFile(destPath);
    }

    /**
     * 裁剪图片到指定尺寸
     *
     * @param sourcePath 源图片路径
     * @param destPath   目标图片路径
     * @param x          裁剪区域左上角x坐标
     * @param y          裁剪区域左上角y坐标
     * @param width      裁剪区域宽度
     * @param height     裁剪区域高度
     */
    public static void cropImage(String sourcePath, String destPath, int x, int y, int width, int height) throws IOException {
        Thumbnails.of(sourcePath)
                .sourceRegion(x, y, width, height)
                .size(width, height)
                .toFile(destPath);
    }

    /**
     * 压缩图片
     *
     * @param inputFile  输入图片文件
     * @param outputFile 输出图片文件
     * @param maxSizeKB  最大大小（KB）
     * @throws IOException
     */
    private static void handle(File inputFile, File outputFile, int maxSizeKB) throws IOException {
        // 获取输入图片的大小（字节）
        long inputFileSize = Files.size(inputFile.toPath());

        // 如果图片大小小于 maxSizeKB，则不需要压缩
        if (inputFileSize <= maxSizeKB * 1024L) {
            Files.copy(inputFile.toPath(), outputFile.toPath());
            return;
        }

        // 计算压缩比例
        double scale = Math.sqrt((double) (maxSizeKB * 1024) / inputFileSize);

        // 压缩图片
        Thumbnails.of(inputFile)
                .scale(scale)
                .outputQuality(0.85)
                .toFile(outputFile);

        // 检查压缩后的图片大小
        long outputFileSize = Files.size(outputFile.toPath());

        // 如果压缩后的图片大小仍然大于 maxSizeKB，则进一步压缩
        while (outputFileSize > maxSizeKB * 1024L) {
            scale *= 0.9;
            Thumbnails.of(outputFile)
                    .scale(scale)
                    .outputQuality(0.85)
                    .toFile(outputFile);
            outputFileSize = Files.size(outputFile.toPath());
        }
    }


    public static void main(String[] args) throws IOException {
        compressImage("C:\\Users\\DELL\\Pictures\\大杨管区\\1.png", "C:\\Users\\DELL\\Pictures\\大杨管区\\2.png");
    }

}
