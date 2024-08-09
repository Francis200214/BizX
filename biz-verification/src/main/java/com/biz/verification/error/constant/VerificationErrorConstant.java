package com.biz.verification.error.constant;


import com.biz.common.utils.ErrorCodeConstant;

/**
 * 参数校验异常常量类。
 * <p>
 * 该类包含了多个内部静态类，每个内部类对应一种参数校验错误类型。
 * 每个内部类都定义了一个错误码和对应的错误消息常量。
 * </p>
 *
 * <p>
 * 这些常量是在校验参数业务逻辑中使用，以确保在不同地方抛出属于自己的错误码和错误信息。
 * </p>
 *
 *
 * @author francis
 * @version 1.0
 * @since 2024-08-09
 * @see ErrorCodeConstant
 */
public final class VerificationErrorConstant {

    /**
     * 集合不能为空
     */
    public static class CheckCollectionIsEmptyError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "集合不能为空";
    }

    /**
     * 时间格式错误
     */
    public static class CheckDateTimeFormatError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "时间格式错误";
    }

    /**
     * 双精度浮点数超过最大限制
     */
    public static class CheckDoubleMaxError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "双精度浮点数超过最大限制";
    }

    /**
     * 双精度浮点数低于最小限制
     */
    public static class CheckDoubleMinError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "双精度浮点数低于最小限制";
    }

    /**
     * 单精度浮点数超过最大限制
     */
    public static class CheckFloatMaxError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "单精度浮点数超过最大限制";
    }

    /**
     * 单精度浮点数低于最小限制
     */
    public static class CheckFloatMinError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "单精度浮点数低于最小限制";
    }

    /**
     * 整数超过最大限制
     */
    public static class CheckIntegerMaxError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "整数超过最大限制";
    }

    /**
     * 整数低于最小限制
     */
    public static class CheckIntegerMinError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "整数低于最小限制";
    }

    /**
     * 数据不能为Null
     */
    public static class CheckIsNullError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "数据不能为Null";
    }

    /**
     * 长整数超过最大限制
     */
    public static class CheckLongMaxError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "长整数超过最大限制";
    }

    /**
     * 长整数低于最小限制
     */
    public static class CheckLongMinError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "长整数低于最小限制";
    }

    /**
     * 短整数超过最大限制
     */
    public static class CheckShortMaxError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "短整数超过最大限制";
    }

    /**
     * 短整数低于最小限制
     */
    public static class CheckShortMinError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "短整数低于最小限制";
    }

    /**
     * 超出长度限制
     */
    public static class CheckSizeError {
        public static final int CODE = ErrorCodeConstant.DEFAULT_ERROR.CODE;
        public static final String MESSAGE = "超出长度限制";
    }

}
