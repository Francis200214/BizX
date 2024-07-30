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
 * Redis Geo Utils 空间工具类
 *
 * @author francis
 * @create 2024-04-03 13:20
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisGeoUtils {

    private final GeoOperations<String, Object> geoOperations;

    private final RedisCommonUtils redisCommonUtils;


    /**
     * 添加一个成员经纬度
     *
     * @param key    key
     * @param point  经纬度点
     * @param member 成员名称
     */
    public void add(String key, Point point, Object member) {
        geoOperations.add(key, point, member);
    }

    /**
     * 查询两个成员之间的距离
     *
     * @param key     key
     * @param member1 成员1名称
     * @param member2 成员2名称
     * @return
     */
    public Distance distance(String key, Object member1, Object member2) {
        return geoOperations.distance(key, member1, member2);
    }

    /**
     * 获取指定成员的地理位置的 Geohash 值
     *
     * @param key     key
     * @param members 成员名称
     * @return
     */
    @SafeVarargs
    public final List<String> hash(String key, Object... members) {
        return geoOperations.hash(key, members);
    }

    /**
     * 用于获取指定成员的地理位置坐标
     *
     * @param key     key
     * @param members 成员名称...
     * @return
     */
    @SafeVarargs
    public final List<Point> position(String key, Object... members) {
        return geoOperations.position(key, members);
    }

    /**
     * 查询指定区域内的成员
     *
     * @param key
     * @param within
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> radius(String key, Circle within) {
        return geoOperations.radius(key, within);
    }

}
