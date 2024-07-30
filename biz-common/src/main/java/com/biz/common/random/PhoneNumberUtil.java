package com.biz.common.random;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 本类提供生成随机手机号码的功能。
 */
public final class PhoneNumberUtil {

    /**
     * 中国移动的号段数组。
     */
    private static final String[] CHINA_MOBILE = {
            "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159",
            "182", "183", "184", "187", "188", "178", "147", "172", "198"
    };

    /**
     * 中国联通的号段数组。
     */
    private static final String[] CHINA_UNICOM = {"130", "131", "132", "145", "155", "156", "166", "171", "175", "176", "185", "186", "166"};

    /**
     * 中国电信的号段数组。
     */
    private static final String[] CHINA_TELECOM = {"133", "149", "153", "173", "177", "180", "181", "189", "199"};

    /**
     * 手机号码的后8位随机数字生成位数。
     */
    private static final int RANDOM_MOBILE_SUFFIX = 8;

    /**
     * 运营商前缀代码与号段数组的映射。
     */
    private static final Map<Integer, String[]> OPERATOR_PREFIX_MAP = new HashMap<>();
    /**
     * 运营商代码与枚举值的映射。
     */
    private static final Map<Integer, OperatorEnum> CODE_TO_ENUM_MAP = new HashMap<>();
    /**
     * 运营商代码与名称的映射。
     */
    private static final Map<Integer, String> CODE_TO_NAME_MAP = new HashMap<>();

    /**
     * 静态初始化块，填充映射表。
     */
    static {
        OPERATOR_PREFIX_MAP.put(0, CHINA_MOBILE);
        OPERATOR_PREFIX_MAP.put(1, CHINA_UNICOM);
        OPERATOR_PREFIX_MAP.put(2, CHINA_TELECOM);

        for (OperatorEnum operator : OperatorEnum.values()) {
            CODE_TO_ENUM_MAP.put(operator.code, operator);
            CODE_TO_NAME_MAP.put(operator.code, operator.name);
        }
    }

    /**
     * 根据指定运营商生成一个随机的手机号码。
     *
     * @param operator 运营商枚举，指定生成哪个运营商的号码。
     * @return 生成的11位手机号码。
     * @throws IllegalArgumentException 如果运营商为null，则抛出此异常。
     */
    public static String createPhoneNumber(OperatorEnum operator) {
        if (operator == null) {
            throw new IllegalArgumentException("运营商不能为空");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(getRandomMobilePrefix(operator));
        for (int i = 0; i < RANDOM_MOBILE_SUFFIX; i++) {
            builder.append(RandomUtils.generateNumber(10));
        }
        return builder.toString();
    }

    /**
     * 根据运营商获取随机的手机号码前缀。
     *
     * @param operator 运营商枚举。
     * @return 随机的手机号码前缀。
     * @throws IllegalArgumentException 如果指定的运营商无效，则抛出此异常。
     */
    private static String getRandomMobilePrefix(OperatorEnum operator) {
        String[] prefixes = OPERATOR_PREFIX_MAP.get(operator.code);
        if (prefixes == null) {
            throw new IllegalArgumentException("无效的运营商!");
        }
        return prefixes[RandomUtils.generateNumber(prefixes.length)];
    }

    /**
     * 运营商枚举，定义了三大运营商及其代码。
     */
    @Getter
    @AllArgsConstructor
    public enum OperatorEnum {
        /**
         * 中国移动。
         */
        CHINA_MOBILE(0, "中国移动"),

        /**
         * 中国联通。
         */
        CHINA_UNICOM(1, "中国联通"),

        /**
         * 中国电信。
         */
        CHINA_TELECOM(2, "中国电信");

        /**
         * 运营商代码。
         */
        private final Integer code;
        /**
         * 运营商名称。
         */
        private final String name;

        /**
         * 根据运营商代码获取运营商名称。
         *
         * @param code 运营商代码。
         * @return 运营商名称，如果代码无效则返回null。
         */
        public static String getOperateNameByCode(Integer code) {
            return Optional.ofNullable(CODE_TO_NAME_MAP.get(code)).orElse(null);
        }

        /**
         * 根据运营商代码获取运营商枚举值。
         *
         * @param code 运营商代码。
         * @return 运营商枚举值，如果代码无效则返回null。
         */
        public static OperatorEnum getOperateByCode(Integer code) {
            return Optional.ofNullable(CODE_TO_ENUM_MAP.get(code)).orElse(null);
        }
    }

}
