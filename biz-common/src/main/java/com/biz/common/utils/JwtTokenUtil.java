package com.biz.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JwtToken工具类
 *
 * @author francis
 */
public final class JwtTokenUtil {

    /**
     * JwtToken 加密密钥
     */
    public static final String DEFAULT_SECRET = "";

    /**
     * JwtToken 默认有效期（1天）
     */
    public static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24;

    /**
     * JwtToken 加密算法
     */
    public static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    /**
     * 生成 jwtToken
     *
     * @param key  Jwt Body 的 Key
     * @param data Jwt Body 的值
     * @return token 值
     */
    public static String createToken(String key, Object data) {
        return createToken(DEFAULT_SECRET, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
    }

    /**
     * 生成 token
     *
     * @param secret             密钥
     * @param expire             失效时间
     * @param signatureAlgorithm 加密算法
     * @param key                Jwt Body 的 Key
     * @param data               Jwt Body 的值
     * @return token 值
     */
    public static String createToken(String secret, long expire, SignatureAlgorithm signatureAlgorithm, String key, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, data);
        return create(secret, expire, signatureAlgorithm, map);
    }

    /**
     * 生成 token
     *
     * @param secret             密钥
     * @param expire             失效时间
     * @param signatureAlgorithm 加密算法
     * @param data               Jwt Body 的 key-value
     * @return token 值
     */
    public static String createToken(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return create(secret, expire, signatureAlgorithm, data);
    }


    /**
     * 判断 jwtToken 是否有效
     *
     * @param jwtToken token 信息
     * @return true 有效 false 无效
     */
    public static boolean checkToken(String jwtToken) {
        if (Common.isBlank(jwtToken)) {
            return false;
        }
        return check(jwtToken, DEFAULT_SECRET);
    }

    /**
     * 判断 jwtToken 是否有效
     *
     * @param jwtToken token 信息
     * @param serret   密钥
     * @return true 有效 false 无效
     */
    public static boolean checkToken(String jwtToken, String serret) {
        if (Common.isBlank(jwtToken)) {
            return false;
        }
        return check(jwtToken, serret);
    }


    /**
     * 获取 Jwt Body 中某个 Key 值
     *
     * @param token jwtToken
     * @param key   key
     * @return
     */
    public static Object getData(final String token, final String key) {
        return get(token, key, DEFAULT_SECRET);
    }

    /**
     * 获取 Jwt Body 中某个 Key 值
     *
     * @param token  jwtToken
     * @param key    key
     * @param secret 密钥信息
     * @return
     */
    public static Object getData(String token, String key, String secret) {
        return get(token, key, secret);
    }


    private static Object get(String token, String key, String secret) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claimsJws.getBody().get(key);
    }


    private static String create(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return Jwts.builder()
                //JWT头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .addClaims(data)
                .signWith(signatureAlgorithm, secret)
                .compact();
    }

    private static boolean check(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
