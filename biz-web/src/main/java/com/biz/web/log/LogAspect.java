package com.biz.web.log;

import com.biz.common.concurrent.ExecutorsUtils;
import com.biz.web.log.recorder.LogRecorder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义日志切面
 *
 * @author francis
 * @create 2024-05-31 16:27
 **/
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogRecorder logRecorder;


    private static final ThreadPoolExecutor LOG_HANDLER_EXECUTOR = ExecutorsUtils.buildThreadPoolExecutor();


    @AfterReturning("@annotation(Loggable)")
    public void logMethod(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Loggable loggable = method.getAnnotation(Loggable.class);
        if (loggable == null) {
            return;
        }

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        String operatorId = parser.parseExpression(loggable.operatorId()).getValue(context, String.class);
        String operatorName = parser.parseExpression(loggable.operatorName()).getValue(context, String.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());

        String content = loggable.content()
                .replace("{userId}", operatorId)
                .replace("{now()}", now)
                .replace("{logType}", loggable.logType());

        content = parser.parseExpression(content).getValue(context, String.class);

        logRecorder.record(loggable, operatorId, operatorName, content);
    }







}
