package com.biz.operation.log.replace;

import com.biz.common.spel.SpELUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * {@code DefaultContentReplacer}是{@link ContentReplacer}接口的默认实现。
 *
 * <p>该实现基于字符串的替换方法，结合SpEL表达式和固定的关键字来进行内容替换。</p>
 *
 * <p>主要功能包括通过预定义的关键字进行替换，并支持使用SpEL表达式动态解析内容。</p>
 *
 * <p>该类使用了{@link SpelExpressionParser}作为默认的表达式解析器。</p>
 *
 * <p>方法包括：</p>
 * <ul>
 *     <li>{@link #replace(String, Object)}：使用默认的表达式解析器和上下文进行内容替换。</li>
 *     <li>{@link #replace(String, Object, ExpressionParser)}：使用指定的表达式解析器和上下文进行内容替换。</li>
 *     <li>{@link #parseSpelExpressions(String, StandardEvaluationContext, ExpressionParser)}：解析并替换内容中的SpEL表达式。</li>
 * </ul>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 **/
@Slf4j
public class DefaultContentReplacer implements ContentReplacer {

    /**
     * 默认的表达式解析器，用于解析SpEL表达式。
     */
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 使用默认的表达式解析器和上下文进行内容替换。
     *
     * @param content 原始内容，包含需要替换的占位符或表达式
     * @param context 上下文，用于提供替换所需的数据
     * @return 替换后的内容，所有占位符或表达式均已被替换为具体值
     */
    @Override
    public String replace(String content, Object context) {
        return this.replace(content, context, parser);
    }

    /**
     * 使用指定的表达式解析器和上下文进行内容替换。
     *
     * <p>如果上下文不是{@link StandardEvaluationContext}的实例，将抛出{@link IllegalArgumentException}。</p>
     *
     * @param content 原始内容，包含需要替换的占位符或表达式
     * @param context 上下文，用于提供替换所需的数据
     * @param parser 表达式解析器，用于解析SpEL表达式
     * @return 替换后的内容，所有占位符或表达式均已被替换为具体值
     * @throws IllegalArgumentException 如果上下文不是{@link StandardEvaluationContext}的实例
     */
    @Override
    public String replace(String content, Object context, ExpressionParser parser) {
        if (!(context instanceof StandardEvaluationContext)) {
            throw new IllegalArgumentException("Context must be an instance of StandardEvaluationContext");
        }
        StandardEvaluationContext evalContext = (StandardEvaluationContext) context;
        SpELContentReplacerHelper helper = SpELContentReplacerHelper.builder()
                .content(content)
                .context(context)
                .parser(parser)
                .build();
        helper.replaceByKey("category", "category");
        helper.replaceByKey("subcategory", "subcategory");
        helper.replaceByValue("{now}", Common.now());
        helper.replaceByValue("operationName", SpELUtils.parseExpression("'"+evalContext.lookupVariable("operationName")+"'", evalContext, parser, String.class));

        return parseSpelExpressions(helper.toContent(), evalContext, parser);
    }

    /**
     * 解析并替换内容中的SpEL表达式。
     *
     * <p>该方法用于查找并解析内容中的SpEL表达式，支持格式为<code>#{expression}</code>的表达式。</p>
     *
     * @param content 包含SpEL表达式的内容
     * @param context SpEL解析上下文
     * @param parser 表达式解析器，用于解析SpEL表达式
     * @return 替换后的内容，所有SpEL表达式均已被解析并替换为具体值
     */
    private String parseSpelExpressions(String content, StandardEvaluationContext context, ExpressionParser parser) {
        StringBuilder parsedContent = new StringBuilder();
        int start = 0;
        while (start < content.length()) {
            int openIndex = content.indexOf("#{", start);
            if (openIndex == -1) {
                parsedContent.append(content.substring(start));
                break;
            }
            parsedContent.append(content, start, openIndex);
            int closeIndex = content.indexOf("}", openIndex);
            if (closeIndex == -1) {
                parsedContent.append(content.substring(openIndex));
                break;
            }
            String expression = content.substring(openIndex + 2, closeIndex);
            String value = SpELUtils.parseExpression(expression, context, parser, String.class);
            parsedContent.append(value);
            start = closeIndex + 1;
        }
        return parsedContent.toString();
    }
}
