package com.biz.common.jts;

import com.biz.common.utils.Common;
import lombok.SneakyThrows;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.List;

/**
 * 提供JTS（Java Topology Suite）几何对象的常用操作工具方法。
 *
 * @author francis
 * @create 2023/3/28 18:40
 */
public final class GeometryUtils {

    /**
     * WKTReader实例，用于解析WKT字符串到Geometry对象。
     * 将其实例化为类级变量以避免重复创建，提升性能。
     */
    private static final WKTReader WKT_READER = new WKTReader();

    /**
     * 将给定的几何字符串转换为Geometry对象。
     *
     * @param str WKT（Well-Known Text）格式的几何字符串。
     * @return 对应的Geometry对象，如果输入为空或格式不正确则返回null。
     * @throws ParseException 如果WKT字符串无法解析，则抛出此异常。
     */
    @SneakyThrows(ParseException.class)
    public static Geometry geometryStrToGeometry(final String str) {
        if (Common.isBlank(str)) {
            return null;
        }

        return WKT_READER.read(str);
    }

    /**
     * 将Coordinate列表转换为Geometry对象。
     *
     * @param coordinates Coordinate列表，代表几何对象的点序列。
     * @return 如果输入为空，则返回null；否则返回由这些点构成的Geometry对象。
     */
    public static Geometry coordinatesToGeometry(List<Coordinate> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            return null;
        }

        Coordinate[] coordinatesArray = new Coordinate[coordinates.size()];
        return JTSUtils.GEOMETRY_FACTORY.createPolygon(coordinates.toArray(coordinatesArray));
    }

    /**
     * 计算给定Geometry对象的中心点的经纬度。
     *
     * @param geometry 目标Geometry对象。
     * @return 包含中心点经度和纬度的double数组，如果输入为null则返回null。
     */
    public static double[] center(Geometry geometry) {
        if (geometry == null) {
            return null;
        }

        Point centroid = geometry.getCentroid();
        return Common.toDoubles(centroid.getX(), centroid.getY());
    }

    /**
     * 对两个Geometry对象进行并集运算。
     *
     * @param geo1 第一个Geometry对象。
     * @param geo2 第二个Geometry对象。
     * @return 两个Geometry对象的并集，如果任一输入为null则返回null。
     */
    public static Geometry union(Geometry geo1, Geometry geo2) {
        if (geo1 == null || geo2 == null) {
            return null;
        }

        return geo1.union(geo2);
    }

    /**
     * 对给定的Geometry对象进行缓冲区处理。
     *
     * @param geometry 目标Geometry对象。
     * @param radius 缓冲区半径。
     * @return 缓冲后的Geometry对象，如果输入为null则返回null。
     */
    public static Geometry buffer(Geometry geometry, double radius) {
        if (geometry == null) {
            return null;
        }
        return geometry.buffer(radius);
    }
}
