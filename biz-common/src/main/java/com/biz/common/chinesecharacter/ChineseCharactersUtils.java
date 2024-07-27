package com.biz.common.chinesecharacter;

import com.biz.common.singleton.Singleton;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * 中文转拼音工具类
 *
 * @author francis
 */
public final class ChineseCharactersUtils {

    private static final Singleton<HanyuPinyinOutputFormat> HANYU_PINYIN_OUTPUT_FORMAT_SINGLETON = Singleton.setSupplier(ChineseCharactersUtils::getHanyuPinyinOutputFormat);

    private static final short CHAR = 128;

    /**
     * 获取字符串拼音的第一个字母
     *
     * @param chinese 中文字符串
     * @return 拼音的首字母
     * @throws BadHanyuPinyinOutputFormatCombination 转换错误
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
     * @param chinese 中文字符串
     * @return 拼音全拼
     * @throws BadHanyuPinyinOutputFormatCombination 转换错误
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


    private static HanyuPinyinOutputFormat getHanyuPinyinOutputFormat() {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        return defaultFormat;
    }


}
