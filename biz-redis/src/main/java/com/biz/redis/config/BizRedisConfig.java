package com.biz.redis.config;

import com.biz.redis.utils.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类，配置Redis的连接工厂、序列化方式以及各种操作工具类。
 * 提供了对Redis各种数据结构的操作支持，包括字符串、哈希、列表、集合、有序集合和地理位置。
 *
 * @author francis
 * @since 2024-04-02 08:28
 * @version 1.4.11
 **/
@Configuration
public class BizRedisConfig {

    /**
     * 配置RedisTemplate，设置连接工厂及序列化方式。
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return RedisTemplate实例
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // String的序列化
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringSerializer);
        // hash的key采用String的序列化方式
        template.setHashKeySerializer(stringSerializer);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jackson2JsonRedisSerializerConfig();
        // value采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value也采用jackson序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 配置LettuceConnectionFactory。
     *
     * @return LettuceConnectionFactory实例
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 配置Jackson2JsonRedisSerializer的序列化方式。
     *
     * @return Jackson2JsonRedisSerializer实例
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializerConfig() {
        // jackson序列化所有的类
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // jackson序列化的一些配置
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 配置ListOperations用于操作列表数据结构。
     *
     * @param redisTemplate RedisTemplate实例
     * @return ListOperations实例
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 配置HashOperations用于操作哈希数据结构。
     *
     * @param redisTemplate RedisTemplate实例
     * @return HashOperations实例
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 配置ValueOperations用于操作字符串数据结构。
     *
     * @param redisTemplate RedisTemplate实例
     * @return ValueOperations实例
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 配置SetOperations用于操作集合数据结构。
     *
     * @param redisTemplate RedisTemplate实例
     * @return SetOperations实例
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 配置ZSetOperations用于操作有序集合数据结构。
     *
     * @param redisTemplate RedisTemplate实例
     * @return ZSetOperations实例
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    /**
     * 配置GeoOperations用于操作地理位置数据结构。
     *
     * @param redisTemplate RedisTemplate实例
     * @return GeoOperations实例
     */
    @Bean
    public GeoOperations<String, Object> geoOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForGeo();
    }

    /**
     * 配置RedisCommonUtils用于提供Redis常用操作的工具类。
     *
     * @param redisTemplate RedisTemplate实例
     * @return RedisCommonUtils实例
     */
    @Bean
    public RedisCommonUtils redisCommonUtils(RedisTemplate<String, Object> redisTemplate) {
        return new RedisCommonUtils(redisTemplate);
    }

    /**
     * 配置RedisListUtils用于提供Redis列表操作的工具类。
     *
     * @param listOperations   ListOperations实例
     * @param redisCommonUtils RedisCommonUtils实例
     * @return RedisListUtils实例
     */
    @Bean
    public RedisListUtils redisListUtils(ListOperations<String, Object> listOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisListUtils(listOperations, redisCommonUtils);
    }

    /**
     * 配置RedisMapUtils用于提供Redis哈希操作的工具类。
     *
     * @param hashOperations   HashOperations实例
     * @param redisCommonUtils RedisCommonUtils实例
     * @return RedisMapUtils实例
     */
    @Bean
    public RedisMapUtils redisMapUtils(HashOperations<String, String, Object> hashOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisMapUtils(hashOperations, redisCommonUtils);
    }

    /**
     * 配置RedisStringUtils用于提供Redis字符串操作的工具类。
     *
     * @param valueOperations  ValueOperations实例
     * @param redisCommonUtils RedisCommonUtils实例
     * @return RedisStringUtils实例
     */
    @Bean
    public RedisStringUtils redisStringUtils(ValueOperations<String, Object> valueOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisStringUtils(valueOperations, redisCommonUtils);
    }

    /**
     * 配置RedisSetUtils用于提供Redis集合操作的工具类。
     *
     * @param setOperations    SetOperations实例
     * @param redisCommonUtils RedisCommonUtils实例
     * @return RedisSetUtils实例
     */
    @Bean
    public RedisSetUtils redisSetUtils(SetOperations<String, Object> setOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisSetUtils(setOperations, redisCommonUtils);
    }

    /**
     * 配置RedisZSetUtils用于提供Redis有序集合操作的工具类。
     *
     * @param zSetOperations   ZSetOperations实例
     * @param redisCommonUtils RedisCommonUtils实例
     * @return RedisZSetUtils实例
     */
    @Bean
    public RedisZSetUtils redisZSetUtils(ZSetOperations<String, Object> zSetOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisZSetUtils(zSetOperations, redisCommonUtils);
    }

    /**
     * 配置RedisGeoUtils用于提供Redis地理位置操作的工具类。
     *
     * @param geoOperations    GeoOperations实例
     * @param redisCommonUtils RedisCommonUtils实例
     * @return RedisGeoUtils实例
     */
    @Bean
    public RedisGeoUtils redisGeoUtils(GeoOperations<String, Object> geoOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisGeoUtils(geoOperations, redisCommonUtils);
    }

}
