package com.biz.common.spel;

import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * {@code SpELUtils} 是一个用于处理 Spring Expression Language (SpEL) 表达式的工具类。
 *
 * <p>该类提供了解析 SpEL 表达式、创建 SpEL 上下文以及设置上下文变量的功能。</p>
 *
 * <p>该工具类的主要功能包括：</p>
 * <ul>
 *     <li>{@link #parseExpression(String, StandardEvaluationContext, ExpressionParser, Class)}：解析并评估 SpEL 表达式，返回指定类型的结果。</li>
 *     <li>{@link #createContext(String[], Object[])}：创建一个 {@link StandardEvaluationContext} 实例，并设置变量。</li>
 *     <li>{@link #setVariable(StandardEvaluationContext, String[], Object[])}：根据参数名称和值设置 SpEL 上下文中的变量。</li>
 * </ul>
 *
 * @author francis
 * @since 1.0.1
 **/
@Slf4j
public class SpELUtils {

    /**
     * 解析给定的 SpEL 表达式，并在指定的上下文中评估表达式，返回结果。
     *
     * @param expression 表达式字符串，不能为 {@code null} 或空字符串
     * @param context    表达式评估的上下文，包含变量和函数定义
     * @param parser     用于解析表达式的解析器
     * @param clazz      期望的结果类型
     * @param <T>        结果的泛型类型
     * @return 解析和评估后的结果，类型为 {@code clazz}
     * @throws IllegalArgumentException 如果表达式字符串为空或为 {@code null}，则抛出该异常
     */
    public static <T> T parseExpression(String expression, StandardEvaluationContext context, ExpressionParser parser, Class<T> clazz) {
        if (Common.isBlank(expression)) {
            throw new IllegalArgumentException("expressionString must not be null or blank");
        }
        return parser.parseExpression(expression).getValue(context, clazz);
    }

    /**
     * 创建一个 {@link StandardEvaluationContext} 实例，并根据给定的参数名称和值设置变量。
     *
     * @param paramNames 参数名称数组，每个元素表示一个变量的名称
     * @param args       参数值数组，每个元素对应 {@code paramNames} 中的一个变量
     * @return 创建的 {@link StandardEvaluationContext} 实例，包含设置好的变量
     * @throws IllegalArgumentException 如果参数名称数组和参数值数组的长度不一致，则抛出该异常
     */
    public static StandardEvaluationContext createContext(String[] paramNames, Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        setVariable(context, paramNames, args);
        return context;
    }

    /**
     * 根据参数名称和值设置 {@link StandardEvaluationContext} 实例中的变量。
     *
     * @param standardEvaluationContext 要设置变量的 {@link StandardEvaluationContext} 实例
     * @param paramNames                参数名称数组，每个元素表示一个变量的名称
     * @param args                      参数值数组，每个元素对应 {@code paramNames} 中的一个变量
     * @throws IllegalArgumentException 如果参数名称数组和参数值数组的长度不一致，则抛出该异常
     */
    public static void setVariable(StandardEvaluationContext standardEvaluationContext, String[] paramNames, Object[] args) {
        if (paramNames.length != args.length) {
            throw new IllegalArgumentException("The lengths of paramNames and args must be equal");
        }
        for (int i = 0; i < args.length; i++) {
            standardEvaluationContext.setVariable(paramNames[i], args[i]);
        }
    }

}
