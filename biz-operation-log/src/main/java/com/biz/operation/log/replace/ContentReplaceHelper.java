package com.biz.operation.log.replace;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 内容替换
 *
 * @author francis
 * @since 2024-08-13 16:03
 **/
public class ContentReplaceHelper {

    private final ExpressionParser parser;

    private String content;

    private final StandardEvaluationContext context;

    public ContentReplaceHelper(String content, StandardEvaluationContext context, ExpressionParser parser) {
        this.content = content;
        this.context = context;
        this.parser = parser == null ? new SpelExpressionParser() : parser;
    }


    public void replaceByKey(String contentKey, String parserKey) {
        Object parserKeyValue = context.lookupVariable(parserKey);
        if (parserKeyValue != null) {
            content = content.replace(contentKey, (String) parserKeyValue);
        }
    }


    public void replaceByValue(String contentKey, String value) {
        content = content.replace(contentKey, value);
    }

    public String toContent() {
        return this.content;
    }


    public static ContentReplaceBuilder builder() {
        return new ContentReplaceBuilder();
    }


    public static class ContentReplaceBuilder {

        private ExpressionParser parser;

        private String content;

        private StandardEvaluationContext context;

        public ContentReplaceBuilder parser(ExpressionParser parser) {
            this.parser = parser;
            return this;
        }

        public ContentReplaceBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ContentReplaceBuilder context(Object context) {
            this.context = (StandardEvaluationContext) context;
            return this;
        }

        public ContentReplaceHelper build() {
            return new ContentReplaceHelper(content, context, parser);
        }

    }


}
