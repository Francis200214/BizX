package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * Jwt Key Utils
 *
 * @author francis
 * @create: 2024-01-04 13:37
 **/
public final class KeyUtils {

    /**
     * 密钥 Key
     *
     * @param secret 密钥
     * @return 密钥 Key
     */
    public static Key getKeyFromSecret(Key secret) {
        return getKeyFromSecret(secret, null);
    }

    /**
     * 密钥 Key
     *
     * @param secret 密钥
     * @return 密钥 Key
     */
    public static Key getKeyFromSecret(String secret) {
        return getKeyFromSecret(secret, null);
    }

    /**
     * 密钥 Key
     *
     * @param signatureAlgorithm 加密算法
     * @return 密钥 Key
     */
    public static Key getKeyFromSecret(SignatureAlgorithm signatureAlgorithm) {
        return getKeyFromSecret("", signatureAlgorithm);
    }

    /**
     * 密钥 Key
     *
     * @param secret             密钥
     * @param signatureAlgorithm 加密算法
     * @return 密钥 Key
     */
    public static Key getKeyFromSecret(String secret, SignatureAlgorithm signatureAlgorithm) {
        byte[] keyBytes = Base64.getEncoder().encode(Common.isBlank(secret) ? JwtUtils.DEFAULT_SECRET.getBytes() : secret.getBytes());
        return new SecretKeySpec(keyBytes, signatureAlgorithm == null ? JwtUtils.DEFAULT_SIGNATURE_ALGORITHM.getJcaName() : signatureAlgorithm.getJcaName());
    }

    /**
     * 密钥 Key
     *
     * @param secret             密钥
     * @param signatureAlgorithm 加密算法
     * @return 密钥 Key
     */
    public static Key getKeyFromSecret(Key secret, SignatureAlgorithm signatureAlgorithm) {
        return new SecretKeySpec(secret.getEncoded(), signatureAlgorithm == null ? JwtUtils.DEFAULT_SIGNATURE_ALGORITHM.getJcaName() : signatureAlgorithm.getJcaName());
    }

}
