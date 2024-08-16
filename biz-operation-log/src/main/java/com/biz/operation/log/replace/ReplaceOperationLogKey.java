package com.biz.operation.log.replace;

/**
 * 替换操作日志关键字。
 *
 * <p>该接口主要是提供给使用者实现自定义操作日志替换关键字接口。</p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 *      public class MyReplaceOperationLogKey implements ReplaceOperationLogKey {
 *          public String replace(String content) {
 *              // 自定义实现替换日志关键字，最终返回替换后的日志关键字。
 *              return content.replace("key", "value");
 *          }
 *      }
 *
 * }</pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 **/
public interface ReplaceOperationLogKey {

    /**
     * 替换操作日志关键字。
     *
     * <p>该方法主要提供替换操作日志关键字的实现。</p>
     *
     * <p>在 content 中是有未替换的SpEl表达式的内容，使用者也可以自己将SpEl表达式的内容处理掉。</p>
     *
     * @param content 原始操作日志内容，包含需要替换的占位符和SpEl表达式。
     * @return 替换后的操作日志关键字
     */
    String replace(String content);


}
