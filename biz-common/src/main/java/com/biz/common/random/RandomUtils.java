package com.biz.common.random;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 提供各种随机数生成工具方法的类。
 * 包括生成整数、浮点数、布尔值、随机字符串等方法。
 *
 * <p>
 * 示例用法：
 * </p>
 * <pre>{@code
 * // 生成一个 0（含）到 100（不含）之间的随机整数
 * int randomNumber = RandomUtils.generateNumber(100);
 *
 * // 生成一个 0.0 到 10.0 之间的随机浮点数
 * double randomDouble = RandomUtils.generateDouble(0.0, 10.0);
 *
 * // 生成一个长度为 10 的随机字符串，包含数字、大小写字母
 * String randomString = RandomUtils.generateStr(10);
 *
 * // 随机生成一个布尔值
 * boolean randomBoolean = RandomUtils.generateBoolean();
 * }
 * </pre>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
public final class RandomUtils {

    /**
     * 数字字符集合。
     */
    public static final String NUMBER = "0123456789";

    /**
     * 小写字母字符集合。
     */
    public static final String LOWER_CASE_CHAR = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 大写字母字符集合。
     */
    public static final String UPPERCASE_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 数字、大小写字母字符集合。
     */
    public static final String ALL_CHAR_NUMBER = LOWER_CASE_CHAR + UPPERCASE_CHAR + NUMBER;

    /**
     * SecureRandom实例，懒加载且线程安全。
     */
    private static class SecureRandomHolder {
        static final SecureRandom INSTANCE;

        static {
            try {
                INSTANCE = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    /**
     * 生成一个 0 到指定范围（end-1）的随机整数。
     *
     * @param end 随机数的上限，不包括在内。
     * @return 生成的随机整数。
     */
    public static int generateNumber(int end) {
        return getThreadLocalRandom().nextInt(end);
    }

    /**
     * 生成指定范围内的随机整数。
     *
     * @param start 随机数的下限。
     * @param end   随机数的上限，不包括在内。
     * @return 生成的随机整数。
     */
    public static int generateNumber(int start, int end) {
        return getThreadLocalRandom().nextInt(start, end);
    }

    /**
     * 生成指定范围内的随机浮点数。
     *
     * @param start 随机数的下限。
     * @param end   随机数的上限。
     * @return 生成的随机浮点数。
     */
    public static double generateDouble(double start, double end) {
        return getThreadLocalRandom().nextDouble(start, end);
    }

    /**
     * 随机生成一个布尔值。
     *
     * @return 生成的随机布尔值。
     */
    public static boolean generateBoolean() {
        return getThreadLocalRandom().nextBoolean();
    }

    /**
     * 生成一个长度为 10 的随机字符串，包含数字、大小写字母。
     *
     * @return 生成的随机字符串。
     */
    public static String generateStr() {
        return generateStr(10);
    }

    /**
     * 生成指定长度的随机字符串，包含数字、大小写字母。
     *
     * @param length 随机字符串的长度。
     * @return 生成的随机字符串。
     */
    public static String generateStr(int length) {
        ThreadLocalRandom random = getThreadLocalRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR_NUMBER.charAt(random.nextInt(ALL_CHAR_NUMBER.length())));
        }
        return sb.toString();
    }

    /**
     * 获取一个使用 SHA1PRNG 算法的 SecureRandom 实例。
     *
     * @return SecureRandom 实例。
     */
    public static SecureRandom getSHA1PRNGSecureRandom() {
        return SecureRandomHolder.INSTANCE;
    }

    /**
     * 获取当前线程的 ThreadLocalRandom 实例。
     *
     * @return ThreadLocalRandom 实例。
     */
    public static ThreadLocalRandom getThreadLocalRandom() {
        return ThreadLocalRandom.current();
    }
}
