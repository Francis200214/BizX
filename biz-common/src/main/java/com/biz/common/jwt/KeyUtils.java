package com.biz.common.jwt;

import com.biz.common.utils.Common;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * 该类提供生成JWT密钥Key的工具方法。
 *
 * @author francis
 * @since 2024-01-04 13:37
 **/
public final class KeyUtils {

    /**
     * 根据密钥字符串生成Key。
     *
     * @param secret 密钥字符串。
     * @return 生成的Key对象。
     */
    public static Key getKeyFromSecret(Key secret) {
        return getKeyFromSecret(secret, null);
    }

    /**
     * 根据密钥字符串生成Key。
     *
     * @param secret 密钥字符串。
     * @return 生成的Key对象。
     */
    public static Key getKeyFromSecret(String secret) {
        return getKeyFromSecret(secret, null);
    }

    /**
     * 根据签名算法生成Key。
     *
     * @param signatureAlgorithm 签名算法。
     * @return 生成的Key对象。
     */
    public static Key getKeyFromSecret(SignatureAlgorithm signatureAlgorithm) {
        return getKeyFromSecret("", signatureAlgorithm);
    }

    /**
     * 根据密钥字符串和签名算法生成Key。
     *
     * @param secret             密钥字符串。
     * @param signatureAlgorithm 签名算法。
     * @return 生成的Key对象。
     */
    public static Key getKeyFromSecret(String secret, SignatureAlgorithm signatureAlgorithm) {
        byte[] keyBytes = Base64.getEncoder().encode(Common.isBlank(secret) ? JwtUtils.DEFAULT_SECRET.getBytes() : secret.getBytes());
        return new SecretKeySpec(keyBytes, signatureAlgorithm == null ? JwtUtils.DEFAULT_SIGNATURE_ALGORITHM.getJcaName() : signatureAlgorithm.getJcaName());
    }

    /**
     * 根据密钥对象和签名算法生成Key。
     *
     * @param secret             密钥对象。
     * @param signatureAlgorithm 签名算法。
     * @return 生成的Key对象。
     */
    public static Key getKeyFromSecret(Key secret, SignatureAlgorithm signatureAlgorithm) {
        return new SecretKeySpec(secret.getEncoded(), signatureAlgorithm == null ? JwtUtils.DEFAULT_SIGNATURE_ALGORITHM.getJcaName() : signatureAlgorithm.getJcaName());
    }

}
