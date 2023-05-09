package com.biz.common.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 二维码生成工具类
 *
 * @author francis
 */
public final class QrCodeUtils {

    /**
     * 图片的宽度
     */
    private static final int WIDTH = 300;

    /**
     * 图片的高度
     */
    private static final int HEIGHT = 300;

    /**
     * 默认图片的格式
     */
    private static final String DEFAULT_IMAGE_FORMAT = "png";

    /**
     * 字符集编码格式
     */
    private static final String CHARACTER = "utf-8";


    private boolean orCode(String content, String path) {
        // 定义二维码的参数
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        // 定义字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, CHARACTER);
        // 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            // 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            // 写入到本地
            Path file = new File(path).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_IMAGE_FORMAT, file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

