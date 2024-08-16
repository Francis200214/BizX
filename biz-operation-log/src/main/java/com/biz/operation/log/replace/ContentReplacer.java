package com.biz.operation.log.replace;

import org.springframework.expression.ExpressionParser;

/**
 * {@code ContentReplacer}接口定义了内容替换的操作。
 *
 * <p>该接口用于替换字符串中的占位符或表达式。</p>
 *
 * <p>主要用于在日志记录等场景中，将模板化的字符串内容根据上下文动态替换为实际的值。</p>
 *
 * <p>该接口提供了两个方法：</p>
 * <ul>
 *     <li>{@link #replace(String, Object)}：使用默认的替换逻辑，将内容中的占位符或表达式替换为具体的值。</li>
 *     <li>{@link #replace(String, Object, ExpressionParser)}：使用自定义的表达式解析器进行替换，以支持更复杂的表达式解析需求。</li>
 * </ul>
 *
 * <p>实现该接口的类可以提供不同的替换策略，例如基于正则表达式、SpEL（Spring Expression Language）等的替换。</p>
 *
 * @author francis
 * @since 1.0.0
 * @version 1.0.0
 */
public interface ContentReplacer {

    /**
     * 替换内容中的占位符或表达式。
     *
     * <p>此方法使用默认的替换逻辑，根据提供的上下文，将内容中的占位符或表达式替换为具体的值。</p>
     *
     * @param content 原始内容，包含需要替换的占位符或表达式
     * @param context 上下文，用于提供替换所需的数据
     * @return 替换后的内容，所有占位符或表达式均已被替换为具体值
     */
    String replace(String content, Object context);

    /**
     * 替换内容中的占位符或表达式。
     *
     * <p>此方法允许使用自定义的{@link ExpressionParser}，以支持更复杂的表达式解析和替换需求。</p>
     *
     * @param content 原始内容，包含需要替换的占位符或表达式
     * @param context 上下文，用于提供替换所需的数据
     * @param parser 表达式解析器，用于解析表达式
     * @return 替换后的内容，所有占位符或表达式均已被替换为具体值
     */
    String replace(String content, Object context, ExpressionParser parser);

}
