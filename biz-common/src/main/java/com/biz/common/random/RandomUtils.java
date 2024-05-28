package com.biz.common.random;


import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成随机数工具类
 *
 * @author francis
 * @create: 2023-04-17 17:04
 **/
@Slf4j
public final class RandomUtils {

    /**
     * 随机数数字
     */
    public static final String NUMBER = "0123456789";

    /**
     * 随机数小写字符串
     */
    public static final String LOWER_CASE_CHAR = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 随机数大写字符串
     */
    public static final String UPPERCASE_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 小写、大写、数字组合
     */
    public static final String ALL_CHAR_NUMBER = LOWER_CASE_CHAR + UPPERCASE_CHAR + NUMBER;


    /**
     * 生成随机数
     *
     * @param end 随机数结束范围
     * @return
     */
    public static int generateNumber(int end) {
        return getThreadLocalRandom().nextInt(end);
    }

    /**
     * 生成一个范围内的随机数
     *
     * @param start 范围起始值
     * @param end   范围结束值
     * @return
     */
    public static int generateNumber(int start, int end) {
        return getThreadLocalRandom().nextInt(start, end);
    }

    /**
     * 生成一个范围内的随机树
     *
     * @param start 范围起始值
     * @param end   范围结束值
     * @return
     */
    public static double generateDouble(double start, double end) {
        return getThreadLocalRandom().nextDouble(start, end);
    }


    /**
     * 生成随机 boolean
     *
     * @return
     */
    public static boolean generateBoolean() {
        return getThreadLocalRandom().nextBoolean();
    }


    /**
     * 生成数字、小写、大写随机字符串
     *
     * @return
     */
    public static String generateStr() {
        return generateStr(10);
    }

    /**
     * 生成规定长度数字、小写、大写随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String generateStr(int length) {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR_NUMBER.charAt(random.nextInt(ALL_CHAR_NUMBER.length())));
        }

        return sb.toString();
    }


    /**
     * 获取 SHA1PRNG SecureRandom
     *
     * @return SecureRandom
     */
    public static SecureRandom getSHA1PRNGSecureRandom() {
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            if (log.isDebugEnabled()) {
                log.error("getSHA1PRNGSecureRandom error ", e);
            }
        }

        return random;
    }

    /**
     * 获取 ThreadLocalRandom
     *
     * @return ThreadLocalRandom
     */
    public static ThreadLocalRandom getThreadLocalRandom() {
        return ThreadLocalRandom.current();
    }

}
