package com.biz.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;


/**
 * Token工具类
 *
 * @author francis
 */
public final class TokenUtils {

    /**
     * 加密密钥
     */
    private static final String KEY = "template";

    /**
     * 生成token
     *
     * @param userId 用户id
     * @return
     */
    public static String createToken(String userId) {
        String token = JWT.create()
                .withAudience(userId)
                .sign(Algorithm.HMAC256(KEY));
        return token;
    }

    /**
     * 获取 token 中的 UserId
     *
     * @param token token
     * @return
     */
    public static String getUserIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getAudience().get(0);
    }

    /**
     * 检验 token
     *
     * @param token
     * @return
     */
    public static void require(String token) {
        final JWTVerifier build = JWT.require(Algorithm.HMAC256(KEY)).build();
        build.verify(token);
    }


    /**
     * 获取当前登录用户的信息
     * @return
     */
//    public UserInfo getUserInfo(){
//        Object params = RequestUtil.getParams().toString();
//        if (params == null) {
//            return null;
//        }
//        // 根据UserId获取当前登录用户的信息
//        return redisUtil.getUserInfo(com.zny.socialserver.util.TokenUtils.parseTokenUserId(params.toString()) + "");
//    }


}
