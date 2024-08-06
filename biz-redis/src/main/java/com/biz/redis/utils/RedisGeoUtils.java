package com.biz.redis.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;

import java.util.List;

/**
 * Redis Geo Utils 空间工具类，提供地理位置相关的操作。
 * 包括添加地理位置、计算距离、获取Geohash值、查询地理位置坐标及查询指定区域内的成员。
 *
 * @author francis
 * @version 1.4.11
 * @since 2024-04-03 13:20
 * @see org.springframework.data.redis.core.GeoOperations
 * @see RedisCommonUtils
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisGeoUtils {

    private final GeoOperations<String, Object> geoOperations;
    private final RedisCommonUtils redisCommonUtils;

    /**
     * 添加一个成员的地理位置（经纬度）。
     *
     * @param key    缓存键
     * @param point  经纬度点
     * @param member 成员名称
     * @see GeoOperations#add(Object, Point, Object)
     */
    public void addLocation(String key, Point point, Object member) {
        geoOperations.add(key, point, member);
    }

    /**
     * 查询两个成员之间的距离。
     *
     * @param key     缓存键
     * @param member1 成员1名称
     * @param member2 成员2名称
     * @return 成员之间的距离
     * @see GeoOperations#distance(Object, Object, Object)
     */
    public Distance getDistance(String key, Object member1, Object member2) {
        return geoOperations.distance(key, member1, member2);
    }

    /**
     * 获取指定成员的地理位置的 Geohash 值。
     *
     * @param key     缓存键
     * @param members 成员名称
     * @return Geohash值列表
     * @see GeoOperations#hash(Object, Object[])
     */
    @SafeVarargs
    public final List<String> getGeohash(String key, Object... members) {
        return geoOperations.hash(key, members);
    }

    /**
     * 获取指定成员的地理位置坐标。
     *
     * @param key     缓存键
     * @param members 成员名称
     * @return 地理位置坐标列表
     * @see GeoOperations#position(Object, Object[])
     */
    @SafeVarargs
    public final List<Point> getPosition(String key, Object... members) {
        return geoOperations.position(key, members);
    }

    /**
     * 查询指定区域内的成员。
     *
     * @param key    缓存键
     * @param within 指定的圆形区域
     * @return 位于指定区域内的成员
     * @see GeoOperations#radius(Object, Circle)
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getMembersWithinRadius(String key, Circle within) {
        return geoOperations.radius(key, within);
    }
}
