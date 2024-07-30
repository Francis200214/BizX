package com.biz.common.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 二维码生成工具类
 *
 * @author francis
 */
@Slf4j
public final class QrCodeUtils {

    /**
     * 二维码图片的默认宽度
     */
    private static final int WIDTH = 300;

    /**
     * 二维码图片的默认高度
     */
    private static final int HEIGHT = 300;

    /**
     * 默认的图片格式
     */
    private static final String DEFAULT_IMAGE_FORMAT = "png";

    /**
     * 编码格式
     */
    private static final String CHARACTER = "utf-8";

    /**
     * 生成二维码图片
     *
     * @param content  二维码内容
     * @param path     保存二维码图片的路径
     * @return boolean 生成成功返回true，失败返回false
     */
    public static boolean generateQrCode(String content, String path) {
        // 输入验证
        if (content == null || content.trim().isEmpty()) {
            log.error("二维码内容不能为空");
            return false;
        }
        if (path == null || !new File(path).getParentFile().exists()) {
            log.error("指定的保存路径不存在: {}", path);
            return false;
        }

        // 设置编码提示类型和值，包括字符集、纠错级别和边距
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, CHARACTER);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            // 使用MultiFormatWriter编码内容为BitMatrix
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            // 将BitMatrix写入到指定路径的图片文件
            Path file = new File(path).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_IMAGE_FORMAT, file);
            return true;
        } catch (Exception e) {
            log.error("生成二维码失败, 内容: {}, 路径: {}", content, path, e);
            return false;
        }
    }

}
