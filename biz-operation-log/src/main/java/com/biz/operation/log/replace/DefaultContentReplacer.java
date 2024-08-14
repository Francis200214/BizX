package com.biz.operation.log.replace;

import com.biz.common.spel.SpELUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 默认的内容替换实现。
 *
 * <p>该实现基于字符串的replace方法，使用SpEL表达式和固定的关键字进行替换。</p>
 *
 * @author francis
 * @since 2024-08-13 15:33
 **/
@Slf4j
public class DefaultContentReplacer implements ContentReplacer {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Override
    public String replace(String content, Object context) {
        return this.replace(content, context, parser);
    }

    @Override
    public String replace(String content, Object context, ExpressionParser parser) {
        if (!(context instanceof StandardEvaluationContext)) {
            throw new IllegalArgumentException("Context must be an instance of StandardEvaluationContext");
        }
        StandardEvaluationContext evalContext = (StandardEvaluationContext) context;
        ContentReplaceHelper helper = ContentReplaceHelper.builder()
                .content(content)
                .context(context)
                .parser(parser)
                .build();
        helper.replaceByKey("category", "category");
        helper.replaceByKey("subcategory", "subcategory");
        helper.replaceByValue("{now}", Common.now());
        helper.replaceByValue("operationName", SpELUtils.parseExpression("'"+evalContext.lookupVariable("operationName")+"'", evalContext, parser, String.class));

//        Object category = evalContext.lookupVariable("category");
//        Object subcategory = evalContext.lookupVariable("subcategory");
//        content = content
//                .replace("operationName", SpELUtils.parseExpression("'"+evalContext.lookupVariable("operationName")+"'", evalContext, parser, String.class))
//                .replace("now()", Common.now())
//                .replace("category", (category != null ? (String) category : ""))
//                .replace("subcategory", (subcategory != null ? (String) subcategory : ""));

        return parseSpelExpressions(helper.toContent(), evalContext, parser);
    }

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
