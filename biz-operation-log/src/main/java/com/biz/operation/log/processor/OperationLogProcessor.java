package com.biz.operation.log.processor;

import com.biz.operation.log.OperationLog;
import com.biz.operation.log.OperationLogAspect;
import com.biz.operation.log.recorder.OperationLogRecorder;
import com.biz.operation.log.replace.ContentReplacer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * {@code OperationLogProcessor}类负责处理带有{@link OperationLog}注解的方法的日志记录逻辑。
 *
 * <p>该处理器通过拦截方法调用，使用Spring AOP框架在方法执行前后以及抛出异常时，记录相应的操作日志。</p>
 *
 * <p>此类的设计依赖于以下两个组件：</p>
 * <ul>
 *     <li>{@link OperationLogRecorder}: 用于实际记录日志的组件。</li>
 *     <li>{@link ContentReplacer}: 用于动态解析日志内容的组件，支持Spring Expression Language (SpEL)。</li>
 * </ul>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * @Component
 * public class MyService {
 *
 *     @OperationLog(category = "SERVICE_OPERATION", content = "Executing method with args: #{#args}")
 *     public void myMethod(String arg) {
 *         // 方法逻辑
 *     }
 * }
 * }</pre>
 *
 * <p>在以上示例中，当调用{@code myMethod}时，{@code OperationLogProcessor}会在方法执行前、成功返回后以及抛出异常时处理日志。</p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
public class OperationLogProcessor {

    /**
     * 操作日志记录器，用于实际记录日志内容。
     */
    private final OperationLogRecorder operationLogRecorder;

    /**
     * 内容替换器，用于解析日志内容，支持SpEL表达式。
     */
    private final ContentReplacer contentReplacer;

    /**
     * 构造一个新的{@code OperationLogProcessor}实例。
     *
     * @param operationLogRecorder 操作日志记录器，负责记录日志
     * @param contentReplacer 内容替换器，用于解析日志内容
     */
    public OperationLogProcessor(OperationLogRecorder operationLogRecorder, ContentReplacer contentReplacer) {
        this.operationLogRecorder = operationLogRecorder;
        this.contentReplacer = contentReplacer;
    }

    /**
     * 在方法执行前处理日志逻辑。
     *
     * <p>此方法主要用于在目标方法执行之前，解析并设置日志内容。</p>
     *
     * @param joinPoint 切入点信息，包含目标方法的上下文
     * @see JoinPoint
     * @see MethodSignature
     * @see StandardEvaluationContext
     */
    public void processBeforeMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = signature.getMethod().getAnnotation(OperationLog.class);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("parameterNames", signature.getParameterNames());
        context.setVariable("args", joinPoint.getArgs());
        String content = contentReplacer.replace(operationLog.content(), context);
        operationLogRecorder.setContent(joinPoint, content);
        operationLogRecorder.setOperationLog(operationLog);
    }

    /**
     * 在方法成功返回后处理日志逻辑。
     *
     * <p>此方法主要用于在目标方法成功执行后，记录操作日志。</p>
     *
     * <p>该方法调用 {@link OperationLogRecorder#record(JoinPoint, String, Throwable)} 来实际记录日志，
     * 其中 {@link OperationLogRecorder} 是处理日志记录的主要类。</p>
     *
     * <p>此方法通常会被 {@link OperationLogAspect} 的 AOP 切面逻辑所调用，
     * 用于在带有 {@link OperationLog} 注解的方法执行完成后自动记录日志。</p>
     *
     * @param joinPoint 切入点信息，包含目标方法的上下文
     * @see JoinPoint
     * @see OperationLogRecorder
     * @see OperationLogAspect
     */
    public void processAfterReturning(JoinPoint joinPoint) {
        operationLogRecorder.record(joinPoint, null, null);
    }

    /**
     * 在方法抛出异常后处理日志逻辑。
     *
     * <p>此方法主要用于在目标方法执行过程中抛出异常时，记录操作日志并捕获异常信息。</p>
     *
     * <p>该方法调用 {@link OperationLogRecorder#record(JoinPoint, String, Throwable)} 来实际记录日志，
     * 并通过传递异常对象 {@link Throwable} 来记录发生的异常信息。</p>
     *
     * <p>此方法通常会被 {@link OperationLogAspect} 的 AOP 切面逻辑所调用，
     * 用于在带有 {@link OperationLog} 注解的方法执行过程中抛出异常时自动记录日志。</p>
     *
     * @param joinPoint 切入点信息，包含目标方法的上下文
     * @param e 方法执行过程中抛出的异常
     * @see JoinPoint
     * @see OperationLogRecorder
     * @see OperationLogAspect
     * @see Throwable
     */
    public void processAfterThrowing(JoinPoint joinPoint, Throwable e) {
        operationLogRecorder.record(joinPoint, null, e);
    }

}
