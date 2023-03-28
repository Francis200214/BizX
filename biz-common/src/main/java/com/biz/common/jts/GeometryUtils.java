package com.biz.common.jts;

import com.biz.common.utils.Common;
import lombok.SneakyThrows;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;

import java.util.List;

/**
 * Geometry（墨卡托）工具类
 *
 * @author francis
 * @create 2023/3/28 18:40
 */
public final class GeometryUtils {

    /**
     * Geometry字符串 转换成 Geometry对象
     *
     * @param str Geometry字符串
     * @return Geometry对象
     */
    @SneakyThrows
    public static Geometry geometryStrToGeometry(final String str) {
        if (Common.isBlank(str)) {
            return null;
        }

        return new WKTReader().read(str);
    }

    /**
     * List<Coordinate> 组合成 Geometry
     *
     * @param coordinates
     * @return
     */
    public static Geometry coordinatesToGeometry(List<Coordinate> coordinates) {
        Coordinate[] coordinatesArray = new Coordinate[coordinates.size()];
        return JTSUtils.GEOMETRY_FACTORY.createPolygon(coordinates.toArray(coordinatesArray));
    }

    /**
     * 两个 Geometry 对象进行并集操作
     *
     * @param geo1 Geometry 对象1
     * @param geo2 Geometry 对象2
     * @return 并集操作后的 Geometry 对象
     */
    public static Geometry union(Geometry geo1, Geometry geo2) {
        return geo1.union(geo2);
    }

    /**
     * 两个 Geometry 对象取交集
     *
     * @param geo1 Geometry 对象1
     * @param geo2 Geometry 对象2
     * @return 做交集后 Geometry 对象
     */
    public static Geometry intersection(Geometry geo1, Geometry geo2) {
        return geo1.intersection(geo2);
    }

    /**
     * 两个 Geometry 对象取差集
     *
     * @param geo1 Geometry 对象1
     * @param geo2 Geometry 对象2
     * @return 做差集后 Geometry 对象
     */
    public static Geometry difference(Geometry geo1, Geometry geo2) {
        return geo1.difference(geo2);
    }

    /**
     * geo1 是否包含 geo2 图形
     *
     * @param geo1 geo1
     * @param geo2 geo2
     * @return true 包含 false 不包含
     */
    public static boolean contains(Geometry geo1, Geometry geo2) {
        return geo1.contains(geo2);
    }

    /**
     * 在 geo1 是否含有 geo2 图形
     *
     * @param geo1 geo1
     * @param geo2 geo2
     * @return true 含有 false 不含有
     */
    public static boolean within(Geometry geo1, Geometry geo2) {
        return geo1.within(geo2);
    }

    /**
     * geo1 是否覆盖在 geo2 图形上
     *
     * @param geo1 geo1
     * @param geo2 geo2
     * @return true 覆盖 false 不覆盖
     */
    public static boolean covers(Geometry geo1, Geometry geo2) {
        return geo1.covers(geo2);
    }

    /**
     * 两个 Geometry 图形是否相交
     *
     * @param geo1
     * @param geo2
     * @return true 相交 false 不相交
     */
    public static boolean intersects(Geometry geo1, Geometry geo2) {
        return geo1.intersects(geo2);
    }

    /**
     * 两个 Geometry 图形是否不相交
     *
     * @param geo1
     * @param geo2
     * @return true 不相交 false 相交
     */
    public static boolean disjoint(Geometry geo1, Geometry geo2) {
        return geo1.disjoint(geo2);
    }

    /**
     * 两个 Geometry 图形是否重叠
     *
     * @param geo1
     * @param geo2
     * @return true 重叠 false 不重叠
     */
    public static boolean overlaps(Geometry geo1, Geometry geo2) {
        return geo1.overlaps(geo2);
    }

    /**
     * 两个 Geometry 图形是否相同
     *
     * @param geo1
     * @param geo2
     * @return true 相同 false 不相同
     */
    public static boolean equals(Geometry geo1, Geometry geo2) {
        return geo1.equals(geo2);
    }

    /**
     * Geometry 图形缓冲
     *
     * @param geometry
     * @return
     */
    public static Geometry buffer(Geometry geometry) {
        return geometry.buffer(0D);
    }


}
