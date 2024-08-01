package com.biz.common.spel;

import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpEL (Spring Expression Language) 工具类，提供解析表达式和创建上下文的功能。
 *
 * @author francis
 * @since 2024-06-03 13:58
 **/
@Slf4j
public class SpELUtils {

    /**
     * 解析给定的SpEL表达式，在给定的上下文中评估表达式并返回结果。
     *
     * @param expression 表达式字符串
     * @param context    表达式评估的上下文
     * @param parser     用于解析表达式的解析器
     * @param clazz      期望的结果类型
     * @param <T>        结果的泛型类型
     * @return 表达式解析和评估后的结果
     * @throws IllegalArgumentException 如果表达式字符串为空或白名单，则抛出运行时异常
     */
    public static <T> T parseExpression(String expression, StandardEvaluationContext context, ExpressionParser parser, Class<T> clazz) {
        if (Common.isBlank(expression)) {
            throw new IllegalArgumentException("expressionString must not be null or blank");
        }
        return parser.parseExpression(expression).getValue(context, clazz);
    }

    /**
     * 创建一个StandardEvaluationContext实例，并根据参数名称和值设置变量。
     *
     * @param paramNames 参数名称数组
     * @param args       参数值数组
     * @return 创建的StandardEvaluationContext实例
     * @throws IllegalArgumentException 如果参数名称和参数值数组长度不一致，则抛出运行时异常
     */
    public static StandardEvaluationContext createContext(String[] paramNames, Object[] args) {
        if (paramNames.length != args.length) {
            throw new IllegalArgumentException("The lengths of paramNames and args must be equal");
        }

        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return context;
    }
}
