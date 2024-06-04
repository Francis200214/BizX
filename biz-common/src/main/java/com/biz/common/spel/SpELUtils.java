package com.biz.common.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpEL 工具类
 *
 * @author francis
 * @create 2024-06-03 13:58
 **/
public class SpELUtils {

    private static final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 解析 SpEL 表达式
     *
     * @param expression SpEL 表达式
     * @param context    上下文
     * @param clazz      返回值类型
     * @param <T>        返回值类型
     * @return 解析后的值
     */
    public static <T> T parseExpression(String expression, StandardEvaluationContext context, Class<T> clazz) {
        return parser.parseExpression(expression).getValue(context, clazz);
    }

    /**
     * 创建上下文并设置变量
     *
     * @param paramNames 参数名称数组
     * @param args       参数值数组
     * @return StandardEvaluationContext 对象
     */
    public static StandardEvaluationContext createContext(String[] paramNames, Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return context;
    }

}
