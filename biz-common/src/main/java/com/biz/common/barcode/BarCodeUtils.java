package com.biz.common.barcode;

import com.biz.common.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * <p>
 * 条形码工具类，提供生成条形码文件的方法。
 * </p>
 * <p>
 * 该工具类封装了对Code128格式条形码的生成逻辑，支持自定义条形码的高度、宽度、是否隐藏文本及是否在两侧留白。
 * </p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     File barcodeFile = BarCodeUtils.generate("1234567890", "/path/to/barcode.png");
 * }
 * </pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
@Slf4j
public final class BarCodeUtils {

    /**
     * PNG图片格式
     */
    private static final String IMAGE_PNG = "image/png";

    /**
     * 分辨率
     */
    private static final int RESOLUTION_RATIO = 150;

    /**
     * 默认条形码高度
     */
    private static final double DEFAULT_BAR_HEIGHT = 9.0D;

    /**
     * 默认模块宽度
     */
    private static final double DEFAULT_MODULE_WIDTH = 0.09D;

    /**
     * 生成条形码文件
     *
     * <p>该方法生成一个包含指定文本内容的条形码，并将其保存到指定路径。</p>
     *
     * @param text 条形码的文本内容，不能为空
     * @param path 生成条形码的文件目录，不能为空
     * @return 返回生成的条形码文件对象
     * @throws IllegalArgumentException 当文本内容或路径为空时抛出
     * @see FileUtils#createFileIfNotExists(File)
     */
    public static File generate(String text, String path) {
        if (text == null || path == null) {
            throw new IllegalArgumentException("Text and path must not be null");
        }
        File file = new File(path);
        FileUtils.createFileIfNotExists(file);
        try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            generateBarCode128(text, 30.0, DEFAULT_MODULE_WIDTH, true, true, outputStream);
        } catch (Exception e) {
            log.error("generate error", e);
            // 根据实际情况，可以抛出自定义异常或进行其他错误处理
        }
        return file;
    }

    /**
     * 生成条形码【code128】
     *
     * <p>该方法使用Code128标准生成条形码，并将生成的条形码输出到指定的输出流。</p>
     *
     * @param text          要生成的条形码的文本内容，不能为空
     * @param height        条形码的高度，为空时使用默认高度
     * @param width         条形码的宽度，为空时使用默认宽度
     * @param withQuietZone 是否在两侧留白
     * @param hideText      是否隐藏条形码下方的可读文本
     * @param outputStream  输出流，不能为空
     * @throws RuntimeException 当生成条形码过程中发生IO异常时抛出
     * @see Code128Bean
     * @see HumanReadablePlacement
     */
    private static void generateBarCode128(String text, Double height, Double width, boolean withQuietZone, boolean hideText, OutputStream outputStream) {
        Code128Bean code128Bean = new Code128Bean();
        // 设置两侧是否留白
        code128Bean.doQuietZone(withQuietZone);
        // 设置条形码高度和宽度
        code128Bean.setBarHeight(height != null ? height : DEFAULT_BAR_HEIGHT);
        if (width != null) {
            code128Bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            code128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }

        try {
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(outputStream, IMAGE_PNG, RESOLUTION_RATIO, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            code128Bean.generateBarcode(canvas, text);
            canvas.finish();
        } catch (IOException e) {
            log.error("generateBarCode128 error", e);
            throw new RuntimeException(e);
        }
    }
}
