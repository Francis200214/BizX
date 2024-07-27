package com.biz.common.barcode;

import com.biz.common.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

/**
 * 条形码工具类
 *
 * @author francis
 */
@Slf4j
public final class BarCodeUtils {

    /**
     * 图片类型
     */
    private static final String IMAGE_PNG = "image/png";
    /**
     * 分辨率
     */
    private static final int RESOLUTION_RATIO = 150;

    /**
     * 生成条形码文件
     *
     * @param text 条形码的文本内容
     * @param path 生成条形码的文件目录
     * @return 返回生成的条形码文件
     */
    public static File generate(String text, String path) {
        File file = new File(path);
        FileUtils.createFileIfNotExists(file);
        try {
            OutputStream outputStream = Files.newOutputStream(file.toPath());
            generateBarCode128(text, 30.0, 0.09, true, true, outputStream);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("generate error ", e);
            }
        }
        return file;
    }



    /**
     * 生成条形码【code128】
     *
     * @param text          要生成的文本
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param hideText      是否隐藏可读文本
     * @param outputStream  输出流
     */
    private static void generateBarCode128(String text, Double height, Double width, boolean withQuietZone, boolean hideText, OutputStream outputStream) {
        Code128Bean code128Bean = new Code128Bean();
        // 设置两侧是否留白
        code128Bean.doQuietZone(withQuietZone);
        // 设置条形码高度和宽度
        code128Bean.setBarHeight(height != null ? height : 9.0D);
        if (width != null) {
            code128Bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            code128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(outputStream, IMAGE_PNG, RESOLUTION_RATIO, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        // 生成条形码
        code128Bean.generateBarcode(canvas, text);
        try {
            canvas.finish();

        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.error("generateBarCode128 error ", e);
            }

        }
    }


}