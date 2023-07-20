package com.biz.common.random;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生成手机号
 *
 * @author francis
 * @create: 2023-07-20 10:15
 **/
public final class PhoneNumberUtil {

    /**
     * 中国移动
     */
    private static final String[] CHINA_MOBILE = {
            "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159",
            "182", "183", "184", "187", "188", "178", "147", "172", "198"
    };

    /**
     * 中国联通
     */
    private static final String[] CHINA_UNICOM = {"130", "131", "132", "145", "155", "156", "166", "171", "175", "176", "185", "186", "166"};

    /**
     * 中国电信
     */
    private static final String[] CHINA_TELECOM = {"133", "149", "153", "173", "177", "180", "181", "189", "199"};

    /**
     * 生成手机号后8位数字
     */
    private static final int RANDOM_MOBILE_SUFFIX = 8;


    /**
     * 生成手机号
     *
     * @param operator 运营商识别码
     * @return 11位手机号
     */
    public static String createPhoneNumber(OperatorEnum operator) {
        StringBuilder builder = new StringBuilder();
        // 拼接手机号前三位
        builder.append(createMobilePrefix(operator));
        for (int i = 0; i < RANDOM_MOBILE_SUFFIX; i++) {
            builder.append(RandomUtils.generateNumber(10));
        }
        return builder.toString();
    }


    /**
     * 创建手机号的前三位
     *
     * @param operator 运营商
     * @return 手机号的前三位
     */
    private static String createMobilePrefix(OperatorEnum operator) {
        // 手机号前三位
        String mobilePrefix = null;
        // 随机生成指定运营商中的手机前三位
        switch (operator) {
            // 中国移动
            case CHINA_MOBILE:
                mobilePrefix = CHINA_MOBILE[RandomUtils.generateNumber(CHINA_MOBILE.length)];
                break;
            // 中国联通
            case CHINA_UNICOM:
                mobilePrefix = CHINA_UNICOM[RandomUtils.generateNumber(CHINA_UNICOM.length)];
                break;
            // 中国电信
            case CHINA_TELECOM:
                mobilePrefix = CHINA_TELECOM[RandomUtils.generateNumber(CHINA_TELECOM.length)];
                break;
            default:
                throw new RuntimeException("无效的运营商!");
        }
        return mobilePrefix;
    }


    @Getter
    @AllArgsConstructor
    public enum OperatorEnum {
        /**
         * 中国移动
         */
        CHINA_MOBILE(0, "中国移动"),
        /**
         * 中国联通
         */
        CHINA_UNICOM(1, "中国联通"),
        /**
         * 中国电信
         */
        CHINA_TELECOM(2, "中国电信");

        /**
         * 运营商识别码
         */
        private final Integer code;

        /**
         * 运营商名称
         */
        private final String name;

        /**
         * 通过运营商识别码获取运营商名称
         *
         * @param code 运营商识别码
         * @return 运营商名称
         */
        public static String getOperateNameByCode(Integer code) {
            // 循环遍历所有枚举
            for (OperatorEnum value : OperatorEnum.values()) {
                // 返回当前运营商名称
                if (value.code.equals(code)) {
                    return value.name;
                }
            }
            return null;
        }

        /**
         * 通过运营商识别码获取运营商名称
         *
         * @param code 运营商识别码
         * @return 运营商名称
         */
        public static OperatorEnum getOperateByCode(Integer code) {
            // 循环遍历所有枚举
            for (OperatorEnum value : OperatorEnum.values()) {
                // 返回当前运营商名称
                if (value.code.equals(code)) {
                    return value;
                }
            }
            return null;
        }

    }


}
