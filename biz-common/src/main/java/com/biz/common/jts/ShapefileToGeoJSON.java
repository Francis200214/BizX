package com.biz.common.jts;

import com.biz.common.concurrent.ExecutorsUtils;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Shapefile To GeoJSON
 *
 * @author francis
 * @create 2024-07-22 13:20
 **/
@Slf4j
public class ShapefileToGeoJSON {

    private static final String CHARSET = "GBK";
    private static final String SHP = ".shp";
    private static final String GEO_JSON = ".geojson";
    private static final ExecutorService EXECUTOR_SERVICE = ExecutorsUtils.buildScheduledExecutorService();


    /**
     * 递归遍历目录下所有的.shp文件，然后将内容转成 GeoJSON 文件的方法
     *
     * @param directory 文件夹
     */
    public static void traverseDirectory(File directory) throws IOException {
        // 获取文件夹下的所有文件和文件夹
        List<File> files;
        try (Stream<Path> paths = Files.list(directory.toPath())) {
            files = paths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("Error listing files", e);
            return;
        }

        files.stream()
                .filter(File::isDirectory)
                .forEach(file -> EXECUTOR_SERVICE.submit(() -> {
                    try {
                        traverseDirectory(file);
                    } catch (IOException e) {
                        // 处理异常，例如记录错误
                        log.error("Error traversing directory", e);
                    }
                }));


        files.stream()
                .filter(file -> file.getName().toLowerCase().endsWith(SHP))
                .forEach(file -> EXECUTOR_SERVICE.submit(() -> convertShpToGeoJSON(file)));

        EXECUTOR_SERVICE.shutdown();

        try {
            // 等待所有任务完成或超时
            boolean terminated = EXECUTOR_SERVICE.awaitTermination(1, TimeUnit.HOURS);
            if (!terminated) {
                // 处理未成功终止的情况，例如记录日志或进行资源清理
                System.err.println("ExecutorService did not terminate within the specified time.");
            }
        } catch (InterruptedException e) {
            log.error("The conversion process is interrupted", e);
            Thread.currentThread().interrupt(); // 恢复中断状态
        }
    }

    /**
     * 将 .shp 文件转换为 .geojson 文件的方法
     *
     * @param shpFile
     */
    public static void convertShpToGeoJSON(File shpFile) {
        String shpFilePath = shpFile.getAbsolutePath();
        String geoJsonFilePath = shpFilePath.substring(0, shpFilePath.lastIndexOf(".")) + GEO_JSON;

        try {
            // 获取 SHP 文件的数据存储
            ShapefileDataStore dataStore = new ShapefileDataStore(shpFile.toURI().toURL());
            // 处理中文乱码
            dataStore.setCharset(Charset.forName(CHARSET));

            // 获取特征集合
            SimpleFeatureCollection featureCollection = dataStore.getFeatureSource().getFeatures();

            // 创建 FeatureJSON 对象
            FeatureJSON featureJSON = new FeatureJSON();
            try (FileWriter writer = new FileWriter(geoJsonFilePath)) {
                // 将特征集合写入 GeoJSON 文件
                featureJSON.writeFeatureCollection(featureCollection, writer);
            }

            // 释放数据存储资源
            dataStore.dispose();
        } catch (IOException e) {
            log.error("Description Failed to convert Shapefile to GeoJSON", e);
        }
    }
}
