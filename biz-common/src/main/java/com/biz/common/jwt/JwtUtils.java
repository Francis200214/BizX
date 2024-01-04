package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JwtToken工具类
 *
 * @author francis
 */
@Slf4j
public final class JwtUtils {

    /**
     * Jwt 默认加密密钥
     */
    public static final String DEFAULT_SECRET = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());

    /**
     * Jwt 默认有效期（1天）
     */
    public static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24;

    /**
     * Jwt 默认加密算法
     */
    public static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    /**
     * 生成
     *
     * @param key  JKey
     * @param data 值
     * @return token 值
     */
    public static String createToken(String key, Object data) {
        return createToken(DEFAULT_SECRET, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
    }

    /**
     * 生成
     *
     * @param secret 密钥
     * @param key    key
     * @param data   值
     * @return token 值
     */
    public static String createToken(String secret, String key, Object data) {
        return createToken(secret, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
    }

    /**
     * 生成
     *
     * @param secret 密钥
     * @param key    key
     * @param data   值
     * @return token 值
     */
    public static String createToken(Key secret, String key, Object data) {
        return createToken(secret, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
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
        Map<String, Object> map = new HashMap<>(8);
        map.put(key, data);
        return create(secret, expire, signatureAlgorithm, map);
    }

    /**
     * 生成 Token
     *
     * @param secret             密钥
     * @param expire             失效时间
     * @param signatureAlgorithm 加密算法
     * @param key                Jwt Body 的 Key
     * @param data               Jwt Body 的值
     * @return
     */
    public static String createToken(Key secret, long expire, SignatureAlgorithm signatureAlgorithm, String key, Object data) {
        Map<String, Object> map = new HashMap<>(8);
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

    public static boolean checkToken(String jwtToken, String serret, SignatureAlgorithm signatureAlgorithm) {
        if (Common.isBlank(jwtToken)) {
            return false;
        }
        return check(jwtToken, serret, signatureAlgorithm);
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
     * 获取 Jwt Body 中
     *
     * @param token token
     * @param key
     * @param signatureAlgorithm
     * @return
     */
    public static Object getData(final String token, final String key, final SignatureAlgorithm signatureAlgorithm) {
        return get(token, key, DEFAULT_SECRET, signatureAlgorithm);
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


    /**
     * 获取
     * @param token
     * @return
     */
    public static Object getSub(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        return getSubject(token, secret, signatureAlgorithm);
    }


    public static Object getSub(String token) {
        return getSubject(token, DEFAULT_SECRET, DEFAULT_SIGNATURE_ALGORITHM);
    }

    /**
     * 获取 Jws<Claims>
     *
     * @param token Token 信息
     * @return Jws<Claims>
     */
    public static Jws<Claims> getClaimsJws(String token) {
        Jws<Claims> claimsJws = parseClaimsJws(token, DEFAULT_SECRET, DEFAULT_SIGNATURE_ALGORITHM);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws;
    }

    public static Jws<Claims> getClaimsJws(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret, signatureAlgorithm);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws;
    }


    private static String create(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .addClaims(data)
                .signWith(KeyUtils.getKeyFromSecret(secret, signatureAlgorithm))
                .compact();
    }


    private static String create(Key secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .addClaims(data)
                .signWith(KeyUtils.getKeyFromSecret(secret, signatureAlgorithm))
                .compact();
    }


    private static Object get(String token, String key, String secret) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws.getBody().get(key);
    }


    private static Object get(String token, String key, String secret, SignatureAlgorithm signatureAlgorithm) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret, signatureAlgorithm);
        if (claimsJws == null) {
            return null;
        }
        return claimsJws.getBody().get(key);
    }

    /**
     * 获取 Jwt 中的 Subject
     *
     * @param token  token
     * @param secret 密钥
     * @return Subject 信息
     */
    private static Object getSubject(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret, signatureAlgorithm);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws.getBody().getSubject();
    }



    private static boolean check(String token, String secret) {
        return parseClaimsJws(token, secret) != null;
    }

    private static boolean check(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        return parseClaimsJws(token, secret, signatureAlgorithm) != null;
    }


    /**
     * 解析 Jwt 的 ClaimsJws
     *
     * @param secret 密钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parseClaimsJws(String token, String secret) {
        try {
            JwtParser build = Jwts.parserBuilder()
                    .setSigningKey(KeyUtils.getKeyFromSecret(secret))
                    .build();
            return build.parseClaimsJws(token);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("解析 Jwt 时出现错误 ", e);
            }
        }

        return null;
    }

    /**
     * 解析 Jwt 的 ClaimsJws
     *
     * @param signatureAlgorithm 密钥算法
     * @return Jws<Claims>
     */
    private static Jws<Claims> parseClaimsJws(String token, SignatureAlgorithm signatureAlgorithm) {
        try {
            JwtParser build = Jwts.parserBuilder()
                    .setSigningKey(KeyUtils.getKeyFromSecret(signatureAlgorithm))
                    .build();
            return build.parseClaimsJws(token);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("解析 Jwt 时出现错误 ", e);
            }
        }

        return null;
    }


    /**
     * 解析 Jwt 的 ClaimsJws
     *
     * @param token              token
     * @param secret             密钥
     * @param signatureAlgorithm 密钥算法
     * @return Jws<Claims>
     */
    private static Jws<Claims> parseClaimsJws(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        try {
            JwtParser build = Jwts.parserBuilder()
                    .setSigningKey(KeyUtils.getKeyFromSecret(secret, signatureAlgorithm))
                    .build();
            return build.parseClaimsJws(token);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("解析 Jwt 时出现错误 ", e);
            }
        }

        return null;
    }


}
