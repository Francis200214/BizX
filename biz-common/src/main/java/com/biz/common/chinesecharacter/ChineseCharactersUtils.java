package com.biz.common.chinesecharacter;

import com.biz.common.singleton.Singleton;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>
 * 中文转拼音工具类，提供将中文字符串转换为拼音的功能，包括获取拼音首字母和拼音全拼。
 * </p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     String firstChar = ChineseCharactersUtils.toFirstChar("中文");
 *     String pinyin = ChineseCharactersUtils.toPinyin("中文");
 * }
 * </pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public final class ChineseCharactersUtils {

    /**
     * HanyuPinyinOutputFormat 的单例，用于配置拼音格式。
     */
    private static final Singleton<HanyuPinyinOutputFormat> HANYU_PINYIN_OUTPUT_FORMAT_SINGLETON = Singleton.createWithSupplier(ChineseCharactersUtils::getHanyuPinyinOutputFormat);

    /**
     * 用于判断字符是否为中文字符的阈值
     */
    private static final short CHAR = 128;

    /**
     * 获取字符串拼音的第一个字母
     *
     * <p>该方法将中文字符串中的每个汉字转换为拼音的首字母，非汉字字符保持不变。</p>
     *
     * @param chinese 中文字符串，不能为空
     * @return 拼音的首字母组成的字符串
     * @throws BadHanyuPinyinOutputFormatCombination 如果拼音格式转换出错，则抛出该异常
     * @see PinyinHelper#toHanyuPinyinStringArray(char, HanyuPinyinOutputFormat)
     */
    public static String toFirstChar(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        StringBuilder pinyinStr = new StringBuilder();
        // 转为单个字符
        for (char c : chinese.toCharArray()) {
            if (c > CHAR) {
                pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, HANYU_PINYIN_OUTPUT_FORMAT_SINGLETON.get())[0].charAt(0));
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString();
    }

    /**
     * 汉字转为拼音全拼
     *
     * <p>该方法将中文字符串中的每个汉字转换为对应的拼音全拼，非汉字字符保持不变。</p>
     *
     * @param chinese 中文字符串，不能为空
     * @return 拼音全拼组成的字符串
     * @throws BadHanyuPinyinOutputFormatCombination 如果拼音格式转换出错，则抛出该异常
     * @see PinyinHelper#toHanyuPinyinStringArray(char, HanyuPinyinOutputFormat)
     */
    public static String toPinyin(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        StringBuilder pinyinStr = new StringBuilder();
        for (char c : chinese.toCharArray()) {
            if (c > CHAR) {
                pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, HANYU_PINYIN_OUTPUT_FORMAT_SINGLETON.get())[0]);
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString();
    }

    /**
     * 获取默认的 HanyuPinyinOutputFormat 配置
     *
     * <p>该方法返回一个默认配置的 {@link HanyuPinyinOutputFormat} 实例，设置拼音为小写且无声调。</p>
     *
     * @return 默认配置的 HanyuPinyinOutputFormat 实例
     * @see HanyuPinyinOutputFormat
     * @see HanyuPinyinCaseType
     * @see HanyuPinyinToneType
     */
    private static HanyuPinyinOutputFormat getHanyuPinyinOutputFormat() {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        return defaultFormat;
    }
}
