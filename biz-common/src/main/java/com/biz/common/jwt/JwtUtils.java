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
 * JWT (JSON Web Token) 工具类，提供创建、验证和解析JWT的功能。
 * 使用HS256算法进行加密。
 * 默认密钥使用Base64编码，默认有效期为1天。
 * 支持自定义密钥、有效期和加密算法。
 * 所有的方法都是静态的，不需要实例化。
 *
 * @author francis
 */
@Slf4j
public final class JwtUtils {

    /**
     * JWT默认加密密钥，使用Base64编码的HS256算法密钥。
     */
    public static final String DEFAULT_SECRET = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());

    /**
     * JWT默认有效期，单位为毫秒，默认为1天。
     */
    public static final long DEFAULT_EXPIRE = 1000 * 60 * 60 * 24;

    /**
     * JWT默认加密算法，使用HS256算法。
     */
    public static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    /**
     * 使用默认配置创建JWT Token。
     *
     * @param key  JWT Body中的键
     * @param data JWT Body中的数据
     * @return 生成的JWT Token
     */
    public static String createToken(String key, Object data) {
        return createToken(DEFAULT_SECRET, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
    }

    /**
     * 使用指定密钥和默认配置创建JWT Token。
     *
     * @param secret 密钥
     * @param key    JWT Body中的键
     * @param data   JWT Body中的数据
     * @return 生成的JWT Token
     */
    public static String createToken(String secret, String key, Object data) {
        return createToken(secret, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
    }

    /**
     * 使用指定密钥和默认配置创建JWT Token。
     *
     * @param secret 密钥
     * @param key    JWT Body中的键
     * @param data   JWT Body中的数据
     * @return 生成的JWT Token
     */
    public static String createToken(Key secret, String key, Object data) {
        return createToken(secret, System.currentTimeMillis() + DEFAULT_EXPIRE, DEFAULT_SIGNATURE_ALGORITHM, key, data);
    }


    /**
     * 使用指定配置创建JWT Token。
     *
     * @param secret             密钥
     * @param expire             过期时间，单位为毫秒
     * @param signatureAlgorithm 加密算法
     * @param key                JWT Body中的键
     * @param data               JWT Body中的数据
     * @return 生成的JWT Token
     */
    public static String createToken(String secret, long expire, SignatureAlgorithm signatureAlgorithm, String key, Object data) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(key, data);
        return create(secret, expire, signatureAlgorithm, map);
    }


    /**
     * 使用指定配置创建JWT Token。
     *
     * @param secret             密钥
     * @param expire             过期时间，单位为毫秒
     * @param signatureAlgorithm 加密算法
     * @param key                JWT Body中的键
     * @param data               JWT Body中的数据
     * @return 生成的JWT Token
     */
    public static String createToken(Key secret, long expire, SignatureAlgorithm signatureAlgorithm, String key, Object data) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(key, data);
        return create(secret, expire, signatureAlgorithm, map);
    }


    /**
     * 使用指定配置创建JWT Token。
     *
     * @param secret             密钥
     * @param expire             过期时间，单位为毫秒
     * @param signatureAlgorithm 加密算法
     * @param data               JWT Body中的键值对数据
     * @return 生成的JWT Token
     */
    public static String createToken(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return create(secret, expire, signatureAlgorithm, data);
    }

    /**
     * 验证JWT Token是否有效。
     *
     * @param jwtToken JWT Token
     * @return 如果有效返回true，否则返回false
     */
    public static boolean checkToken(String jwtToken) {
        return checkToken(jwtToken, DEFAULT_SECRET);
    }

    /**
     * 验证JWT Token是否有效。
     *
     * @param jwtToken JWT Token
     * @param secret   密钥
     * @return 如果有效返回true，否则返回false
     */
    public static boolean checkToken(String jwtToken, String secret) {
        if (Common.isBlank(jwtToken)) {
            return false;
        }
        return check(jwtToken, secret);
    }

    /**
     * 验证JWT Token是否有效。
     *
     * @param jwtToken JWT Token
     * @param secret   密钥
     * @return 如果有效返回true，否则返回false
     */
    public static boolean checkToken(String jwtToken, String secret, SignatureAlgorithm signatureAlgorithm) {
        if (Common.isBlank(jwtToken)) {
            return false;
        }
        return check(jwtToken, secret, signatureAlgorithm);
    }


    /**
     * 获取JWT Body中指定键的值。
     *
     * @param token JWT Token
     * @param key   JWT Body中的键
     * @return JWT Body中指定键的值
     */
    public static Object getData(final String token, final String key) {
        return get(token, key, DEFAULT_SECRET);
    }


    /**
     * 从JWT中获取指定键值的数据。
     *
     * @param token              JWT令牌
     * @param key                JWT中的键
     * @param signatureAlgorithm 签名算法
     * @return 键对应的数据，如果JWT无效或键不存在，则返回null。
     */
    public static Object getData(final String token, final String key, final SignatureAlgorithm signatureAlgorithm) {
        return get(token, key, DEFAULT_SECRET, signatureAlgorithm);
    }


    /**
     * 从JWT中获取指定键值的数据。
     *
     * @param token  JWT令牌
     * @param key    JWT中的键
     * @param secret 加密密钥
     * @return 键对应的数据，如果JWT无效或键不存在，则返回null。
     */
    public static Object getData(String token, String key, String secret) {
        return get(token, key, secret);
    }


    /**
     * 从JWT中获取主题(sub)。
     *
     * @param token              JWT令牌
     * @param secret             加密密钥
     * @param signatureAlgorithm 签名算法
     * @return JWT的主题，如果JWT无效，则返回null。
     */
    public static Object getSub(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        return getSubject(token, secret, signatureAlgorithm);
    }

    /**
     * 从JWT中获取主题(sub)。
     *
     * @param token JWT令牌
     * @return JWT的主题，如果JWT无效，则返回null。
     */
    public static Object getSub(String token) {
        return getSubject(token, DEFAULT_SECRET, DEFAULT_SIGNATURE_ALGORITHM);
    }

    /**
     * 解析JWT并返回其声明。
     *
     * @param token JWT令牌
     * @return 解析后的JWT声明，如果JWT无效，则返回null。
     */
    public static Jws<Claims> getClaimsJws(String token) {
        return getClaimsJws(token, DEFAULT_SECRET, DEFAULT_SIGNATURE_ALGORITHM);
    }

    /**
     * 解析JWT并返回其声明。
     *
     * @param token              JWT令牌
     * @param secret             加密密钥
     * @param signatureAlgorithm 签名算法
     * @return 解析后的JWT声明，如果JWT无效，则返回null。
     */
    public static Jws<Claims> getClaimsJws(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret, signatureAlgorithm);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws;
    }

    /**
     * 创建JWT令牌。
     *
     * @param secret             加密密钥
     * @param expire             JWT的过期时间
     * @param signatureAlgorithm 签名算法
     * @param data               JWT中的数据声明
     * @return 创建的JWT令牌字符串
     */
    private static String create(String secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .addClaims(data)
                .signWith(KeyUtils.getKeyFromSecret(secret, signatureAlgorithm))
                .compact();
    }

    /**
     * 创建JWT令牌。
     *
     * @param secret             加密密钥
     * @param expire             JWT的过期时间
     * @param signatureAlgorithm 签名算法
     * @param data               JWT中的数据声明
     * @return 创建的JWT令牌字符串
     */
    private static String create(Key secret, long expire, SignatureAlgorithm signatureAlgorithm, Map<String, Object> data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(expire))
                .addClaims(data)
                .signWith(KeyUtils.getKeyFromSecret(secret, signatureAlgorithm))
                .compact();
    }

    /**
     * 从JWT中获取指定键值的数据。
     *
     * @param token  JWT令牌
     * @param key    JWT中的键
     * @param secret 加密密钥
     * @return 键对应的数据，如果JWT无效或键不存在，则返回null。
     */
    private static Object get(String token, String key, String secret) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws.getBody().get(key);
    }

    /**
     * 从JWT中获取指定键值的数据。
     *
     * @param token              JWT令牌
     * @param key                JWT中的键
     * @param secret             加密密钥
     * @param signatureAlgorithm 签名算法
     * @return 键对应的数据，如果JWT无效或键不存在，则返回null。
     */
    private static Object get(String token, String key, String secret, SignatureAlgorithm signatureAlgorithm) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret, signatureAlgorithm);
        if (claimsJws == null) {
            return null;
        }
        return claimsJws.getBody().get(key);
    }

    /**
     * 从JWT中获取主题(sub)。
     *
     * @param token              JWT令牌
     * @param secret             加密密钥
     * @param signatureAlgorithm 签名算法
     * @return JWT的主题，如果JWT无效，则返回null。
     */
    private static Object getSubject(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        Jws<Claims> claimsJws = parseClaimsJws(token, secret, signatureAlgorithm);
        if (claimsJws == null) {
            return null;
        }

        return claimsJws.getBody().getSubject();
    }

    /**
     * 验证JWT的有效性。
     *
     * @param token  JWT令牌
     * @param secret 加密密钥
     * @return JWT是否有效
     */
    private static boolean check(String token, String secret) {
        return parseClaimsJws(token, secret) != null;
    }

    /**
     * 验证JWT的有效性。
     *
     * @param token              JWT令牌
     * @param secret             加密密钥
     * @param signatureAlgorithm 签名算法
     * @return JWT是否有效
     */
    private static boolean check(String token, String secret, SignatureAlgorithm signatureAlgorithm) {
        return parseClaimsJws(token, secret, signatureAlgorithm) != null;
    }

    /**
     * 解析JWT并返回其声明。
     *
     * @param token  JWT令牌
     * @param secret 加密密钥
     * @return 解析后的JWT声明，如果JWT无效，则返回null。
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
     * 解析JWT并返回其声明。
     *
     * @param token              JWT令牌
     * @param signatureAlgorithm 签名算法
     * @return 解析后的JWT声明，如果JWT无效，则返回null。
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
     * 解析JWT并返回其声明。
     *
     * @param token              JWT令牌
     * @param secret             加密密钥
     * @param signatureAlgorithm 签名算法
     * @return 解析后的JWT声明，如果JWT无效，则返回null。
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
