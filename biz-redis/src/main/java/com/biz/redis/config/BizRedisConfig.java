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
 * Redis 配置
 *
 * @author francis
 * @since 2024-04-02 08:28
 **/
@Configuration
public class BizRedisConfig {


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

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }


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

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    public GeoOperations<String, Object> geoOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForGeo();
    }

    @Bean
    public RedisCommonUtils redisCommonUtils(RedisTemplate<String, Object> redisTemplate) {
        return new RedisCommonUtils(redisTemplate);
    }

    @Bean
    public RedisListUtils redisListUtils(ListOperations<String, Object> listOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisListUtils(listOperations, redisCommonUtils);
    }

    @Bean
    public RedisMapUtils redisMapUtils(HashOperations<String, String, Object> hashOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisMapUtils(hashOperations, redisCommonUtils);
    }

    @Bean
    public RedisStringUtils redisStringUtils(ValueOperations<String, Object> valueOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisStringUtils(valueOperations, redisCommonUtils);
    }

    @Bean
    public RedisSetUtils redisSetUtils(SetOperations<String, Object> setOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisSetUtils(setOperations, redisCommonUtils);
    }

    @Bean
    public RedisZSetUtils redisZSetUtils(ZSetOperations<String, Object> zSetOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisZSetUtils(zSetOperations, redisCommonUtils);
    }

    @Bean
    public RedisGeoUtils redisGeoUtils(GeoOperations<String, Object> geoOperations, RedisCommonUtils redisCommonUtils) {
        return new RedisGeoUtils(geoOperations, redisCommonUtils);
    }

}
