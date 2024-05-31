package com.biz.common.file;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * 图像压缩
 *
 * @author francis
 * @create 2024-05-29 19:40
 **/
@Slf4j
public class ImageCompressor {

    public static final String JPG = ".jpg";
    public static final String JPEG = ".jpeg";
    public static final String PNG = ".png";
    public static final String GIF = ".gif";


    /**
     * 图片压缩
     *
     * @param imageHttpPath
     * @return
     * @throws IOException
     */
    public static byte[] compressor(String imageHttpPath) throws IOException {
        URL imageUrl = new URL(imageHttpPath);
        return handle(ImageIO.read(imageUrl), getFormatName(imageUrl));
    }

    /**
     * 图片压缩
     *
     * @param inputStream
     * @param fileFormatName
     * @return
     * @throws IOException
     */
    public static byte[] compressor(InputStream inputStream, String fileFormatName) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] byteArray = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(byteArray, 0, byteArray.length)) != -1) {
            buffer.write(byteArray, 0, bytesRead);
        }
        inputStream.close();
        byte[] imageBytes = buffer.toByteArray();
        InputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        return handle(ImageIO.read(byteArrayInputStream), fileFormatName);
    }

    /**
     * 压缩图形
     *
     * @param originalImage
     * @return 字节流
     */
    private static byte[] handle(BufferedImage originalImage, String fileFormatName) {
        ImageOutputStream ios = null;
        ImageWriter writer = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ios = ImageIO.createImageOutputStream(baos);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileFormatName);
            if (!writers.hasNext()) {
                throw new IllegalStateException("No writers found");
            }
            writer = writers.next();
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                // 质量在0到1之间，值越低压缩越高
                param.setCompressionQuality(1f);
            }

            // Step 3: 写入压缩图像到ByteArrayOutputStream
            writer.write(null, new IIOImage(originalImage, null, null), param);


            // 获取压缩后的图像字节流
            return baos.toByteArray();

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("图片压缩处理失败 ", e);
            }

        } finally {
            if (writer != null) {
                writer.dispose();
            }

            if (ios != null) {
                try {
                    ios.close();
                } catch (IOException e) {
                    if (log.isDebugEnabled()) {
                        log.error("关闭 ImageOutputStream 流出现了异常 ", e);
                    }
                }
            }
        }
        return null;
    }


    private static String getFormatName(URL imageUrl) {
        String urlString = imageUrl.toString();
        if (urlString.endsWith(JPG) || urlString.endsWith(JPEG)) {
            return JPG;
        } else if (urlString.endsWith(PNG)) {
            return PNG;
        } else if (urlString.endsWith(GIF)) {
            return GIF;
        } else {
            throw new IllegalArgumentException("Unsupported image format: " + urlString);
        }
    }

}

