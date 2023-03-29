package com.biz.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


/**
 * JwtToken工具类
 *
 * @author francis
 */
public final class JwtTokenUtils {

    /**
     * JwtToken 加密密钥
     */
    private final String SECRET;

    /**
     * JwtToken 有效期（15天）
     */
    private final long EXPIRE;

    /**
     * JwtToken 加密算法
     */
    private final SignatureAlgorithm SIGNATURE_ALGORITHM;


    private JwtTokenUtils(String secret, SignatureAlgorithm signatureAlgorithm, long expire) {
        SECRET = secret;
        EXPIRE = expire;
        SIGNATURE_ALGORITHM = signatureAlgorithm;
    }


    public static JwtTokenUtilsBuilder JwtTokenUtilsbuilder() {
        return new JwtTokenUtilsBuilder();
    }





    /**
     * 生成 jwtToken
     *
     * @param key Jwt Body 的 Key
     * @param data Jwt Body 的值
     * @return
     */
    public String createToken(String key, Object data) {
        String jwtToken = Jwts.builder()
                //JWT头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return jwtToken;
    }

    /**
     * 判断 jwtToken 是否存在并有效
     *
     * @Param jwtToken
     */
    public boolean checkToken(String jwtToken) {
        if (Common.isBlank(jwtToken)){
            return false;
        }

        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取 Jwt Body 中某个 Key 值
     *
     * @param jwtToken jwtToken
     * @param key 存在jwt中的哪个key值
     * @return
     */
    public String getDataByJwtToken(final String jwtToken, final String key){
        if (Common.isBlank(jwtToken) || Common.isBlank(key)){
            return null;
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken);
        Claims body = claimsJws.getBody();
        return Common.to(body.get(key));
    }


    public static class JwtTokenUtilsBuilder {
        /**
         * JwtToken 加密密钥
         */
        private String secret;

        /**
         * JwtToken 有效期
         */
        private long expire;

        /**
         * 加密算法
         */
        private SignatureAlgorithm signature_algorithm;

        private JwtTokenUtilsBuilder() {

        }

        public JwtTokenUtilsBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public JwtTokenUtilsBuilder expire(long expire) {
            this.expire = expire;
            return this;
        }

        public JwtTokenUtilsBuilder SignatureAlgorithm(SignatureAlgorithm signature_algorithm) {
            this.signature_algorithm = signature_algorithm;
            return this;
        }

        public JwtTokenUtils builder() {
            return new JwtTokenUtils(secret, signature_algorithm, expire);
        }

    }

}
