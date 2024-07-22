package com.biz.common.jts;

import lombok.extern.slf4j.Slf4j;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;


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


    /**
     * 递归遍历目录下所有的.shp文件，然后将内容转成 GeoJSON 文件的方法
     *
     * @param directory 文件夹
     */
    public static void traverseDirectory(File directory) {
        // 获取文件夹下的所有文件和文件夹
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归调用方法遍历子文件夹
                    traverseDirectory(file);
                } else if (file.getName().toLowerCase().endsWith(SHP)) {
                    // 如果是 .shp 文件，执行转换操作
                    convertShpToGeoJSON(file);
                }
            }
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
            log.error("Shapefile 转成 GeoJSON 失败", e);
        }
    }


}

