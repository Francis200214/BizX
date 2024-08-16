package com.biz.operation.log.replace;

import com.biz.common.bean.BizXBeanUtils;
import com.biz.common.spel.SpELUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
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
public class DefaultContentReplacer implements ContentReplacer, SmartInitializingSingleton {

    /**
     * 使用者自定义操作日志中替换关键字的接口。
     */
    private ReplaceOperationLogKey replaceOperationLogKey;

    /**
     * 默认的表达式解析器，用于解析 SpEL 表达式。
     */
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 当所有 Bean 都加载完成后，获取使用者自定义的{@link ReplaceOperationLogKey}实现 Bean。
     *
     * <p>如果找不到{@link ReplaceOperationLogKey}的实现，则不会执行任何操作。</p>
     */
    @Override
    public void afterSingletonsInstantiated() {
        try {
            this.replaceOperationLogKey = BizXBeanUtils.getBean(ReplaceOperationLogKey.class);
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("ReplaceOperationLogKey Bean is null in DefaultContentReplacer");
            }
        }
    }

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
     * @param content 操作日志原始内容，包含需要替换的占位符或表达式
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
        Object[] args = (Object[]) evalContext.lookupVariable("args");
        String[] parameterNames = (String[])evalContext.lookupVariable("parameterNames");

        SpELUtils.setVariable(evalContext, parameterNames, args);

        // 替换默认的关键字和值
        content = this.replaceCustomKey(content);

        SpELContentReplacerHelper helper = SpELContentReplacerHelper.builder()
                .content(content)
                .context(context)
                .parser(parser)
                .build();
        // 替换框架默认的关键字和值
        this.replaceDefaultKey(helper, evalContext, parser);
        // 替换日志中的SpEl表达式
        helper.replaceForSpEl();
        return helper.toContent();
    }

    /**
     * 替换使用者自定义实现的替换操作日志上下文关键字信息。
     *
     * @param content 操作日志原始内容，包含需要替换的占位符或表达式
     */
    private String replaceCustomKey(String content) {
        if (replaceOperationLogKey != null) {
            content = replaceOperationLogKey.replace(content);
            return content;
        }
        return content;
    }

    /**
     * 替换框架支持的关键字。
     *
     * @param helper SpEl内容替换器
     * @param context context 上下文，用于提供替换所需的数据
     * @param parser 表达式解析器，用于解析SpEL表达式
     */
    private void replaceDefaultKey(SpELContentReplacerHelper helper, StandardEvaluationContext context, ExpressionParser parser) {
        helper.replaceByKey("category", "category");
        helper.replaceByKey("subcategory", "subcategory");
        helper.replaceByValue("{now}", Common.now());
        helper.replaceByValue("operationName", SpELUtils.parseExpression("'"+context.lookupVariable("operationName")+"'", context, parser, String.class));
    }



}
