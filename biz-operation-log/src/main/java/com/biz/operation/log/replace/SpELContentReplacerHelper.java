package com.biz.operation.log.replace;

import com.biz.common.spel.SpELUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * {@code SpELContentReplacerHelper}类用于处理内容替换操作。
 *
 * <p>该类提供了基于SpEL表达式的内容替换功能，支持通过键值或直接值进行字符串替换。
 * 通过构造函数或构建器模式创建实例时，可以指定自定义的表达式解析器和上下文。</p>
 *
 * <p>主要功能包括：</p>
 * <ul>
 *     <li>{@link #replaceByKey(String, String)}：根据指定的键在上下文中查找对应的值，并替换内容中的指定键。</li>
 *     <li>{@link #replaceByValue(String, String)}：直接用指定的值替换内容中的指定键。</li>
 *     <li>{@link #toContent()}：返回替换后的内容。</li>
 * </ul>
 *
 * <p>该类还提供了一个静态内部类{@link ContentReplaceBuilder}，用于构建{@code SpELContentReplacerHelper}实例。</p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * StandardEvaluationContext context = new StandardEvaluationContext();
 * context.setVariable("key", "value");
 * SpELContentReplacerHelper helper = SpELContentReplacerHelper.builder()
 *         .content("some content with key")
 *         .context(context)
 *         .build();
 * helper.replaceByKey("key", "key");
 * String result = helper.toContent();
 * }</pre>
 *
 * <p>在以上示例中，"key"会被替换为上下文中对应的值。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
public class SpELContentReplacerHelper {

    /**
     * 表达式解析器，用于解析SpEL表达式。
     */
    private final ExpressionParser parser;

    /**
     * 原始内容，包含需要替换的占位符或表达式。
     */
    private String content;

    /**
     * SpEL解析上下文，用于提供表达式解析所需的数据。
     */
    private final StandardEvaluationContext context;

    /**
     * 构造一个新的{@code SpELContentReplacerHelper}实例。
     *
     * @param content 需要替换的原始内容
     * @param context SpEL解析上下文，用于提供表达式解析所需的数据
     * @param parser  表达式解析器，用于解析SpEL表达式，如果为{@code null}，则使用默认的{@link SpelExpressionParser}
     */
    public SpELContentReplacerHelper(String content, StandardEvaluationContext context, ExpressionParser parser) {
        this.content = content;
        this.context = context;
        this.parser = parser == null ? new SpelExpressionParser() : parser;
    }

    /**
     * 根据指定的键在上下文中查找对应的值，并替换内容中的指定键。
     *
     * @param contentKey 需要替换的内容中的键
     * @param parserKey  上下文中对应的键
     */
    public void replaceByKey(String contentKey, String parserKey) {
        Object parserKeyValue = context.lookupVariable(parserKey);
        if (parserKeyValue != null) {
            content = content.replace(contentKey, (String) parserKeyValue);
        }
    }

    /**
     * 直接用指定的值替换内容中的指定键。
     *
     * @param contentKey 需要替换的内容中的键
     * @param value      替换成的值
     */
    public void replaceByValue(String contentKey, String value) {
        content = content.replace(contentKey, value);
    }

    /**
     * 使用SpEL表达式替换内容中的占位符。
     */
    public void replaceForSpEl() {
        content = String.valueOf(SpELUtils.parseExpression(content, context, parser, Object.class));
    }

    /**
     * 返回替换后的内容。
     *
     * @return 替换后的内容字符串
     */
    public String toContent() {
        return this.content;
    }

    /**
     * 创建并返回一个新的{@link ContentReplaceBuilder}实例，用于构建{@link SpELContentReplacerHelper}对象。
     *
     * @return 一个新的{@link ContentReplaceBuilder}实例
     */
    public static ContentReplaceBuilder builder() {
        return new ContentReplaceBuilder();
    }

    /**
     * {@code ContentReplaceBuilder}类是一个构建器类，用于构建{@link SpELContentReplacerHelper}实例。
     */
    public static class ContentReplaceBuilder {

        /**
         * 表达式解析器，用于解析SpEL表达式。
         */
        private ExpressionParser parser;

        /**
         * 原始内容，包含需要替换的占位符或表达式。
         */
        private String content;

        /**
         * SpEL解析上下文，用于提供表达式解析所需的数据。
         */
        private StandardEvaluationContext context;

        /**
         * 设置表达式解析器。
         *
         * @param parser 表达式解析器
         * @return 当前的{@link ContentReplaceBuilder}实例
         */
        public ContentReplaceBuilder parser(ExpressionParser parser) {
            this.parser = parser;
            return this;
        }

        /**
         * 设置原始内容。
         *
         * @param content 原始内容
         * @return 当前的{@link ContentReplaceBuilder}实例
         */
        public ContentReplaceBuilder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * 设置SpEL解析上下文。
         *
         * @param context SpEL解析上下文
         * @return 当前的{@link ContentReplaceBuilder}实例
         */
        public ContentReplaceBuilder context(Object context) {
            this.context = (StandardEvaluationContext) context;
            return this;
        }

        /**
         * 构建并返回一个新的{@link SpELContentReplacerHelper}实例。
         *
         * @return 一个新的{@link SpELContentReplacerHelper}实例
         */
        public SpELContentReplacerHelper build() {
            return new SpELContentReplacerHelper(content, context, parser);
        }

    }

}
