package com.biz.common.utils;

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
public final class ChineseCharactersUtil {


    /**
     * 获取字符串拼音的第一个字母
     *
     * @param chinese
     * @return
     */
    public static String toFirstChar(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        StringBuilder pinyinStr = new StringBuilder();
        //转为单个字符
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0));
            }else{
                pinyinStr.append(newChar[i]);
            }
        }
        return pinyinStr.toString();
    }

    /**
     * 汉字转为拼音全拼
     *
     * @param chinese
     * @return
     */
    public static String toPinyin(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        StringBuilder pinyinStr = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0]);
            }else{
                pinyinStr.append(newChar[i]);
            }
        }
        return pinyinStr.toString();
    }

}
