package com.biz.operation.log.replace;

import org.springframework.expression.ExpressionParser;

/**
 * 定义内容替换的接口。
 *
 * <p>该接口用于替换字符串中的占位符或表达式。用户可以实现该接口，提供自定义的替换逻辑。</p>
 *
 * @author francis
 * @since 2024-08-13 15:32
 **/
public interface ContentReplacer {

    /**
     * 替换内容中的占位符或表达式。
     *
     * @param content 原始内容
     * @param context 上下文，用于提供替换所需的数据
     * @return 替换后的内容
     */
    String replace(String content, Object context);


    /**
     * 替换内容中的占位符或表达式。
     *
     * @param content 原始内容
     * @param context 上下文，用于提供替换所需的数据
     * @param parser 表达式解析器，用于解析表达式
     * @return 替换后的内容
     */
    String replace(String content, Object context, ExpressionParser parser);

}
