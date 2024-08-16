package com.biz.common.error;

/**
 * BizX 最高异常类接口。
 * <p>定义了获取错误码和错误信息的方法，用于所有 BizX 相关异常的基类。</p>
 * <p>该抽象类继承自 {@link Exception}，并要求子类实现获取错误码 {@code getCode()} 和错误信息 {@code getMessage()} 的方法。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     public class CustomBizXException extends BizXException {
 *         private final int code;
 *         private final String message;
 *
 *         public CustomBizXException(int code, String message) {
 *             this.code = code;
 *             this.message = message;
 *         }
 *
 *         @Override
 *         public int getCode() {
 *             return code;
 *         }
 *
 *         @Override
 *         public String getMessage() {
 *             return message;
 *         }
 *     }
 * }</pre>
 *
 * <p>该类提供了多个构造函数，以便子类可以根据需要调用父类的构造函数。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public abstract class BizXException extends Exception {

    /**
     * 获取错误码。
     *
     * @return 错误码
     */
    public abstract int getCode();

    /**
     * 获取错误信息。
     *
     * @return 错误信息
     */
    public abstract String getMessage();

    /**
     * 默认构造函数。
     */
    public BizXException() {
        super();
    }

    /**
     * 带错误信息的构造函数。
     *
     * @param message 错误信息
     */
    public BizXException(String message) {
        super(message);
    }

}
