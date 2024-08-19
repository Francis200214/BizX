package com.biz.verification.annotation.check;

import com.biz.common.utils.ErrorCodeConstant;
import com.biz.verification.annotation.error.BizXCheckErrorMessage;
import com.biz.verification.error.constant.VerificationErrorConstant;

import java.lang.annotation.*;

/**
 * 检查 Collection 类型是否可以为空的注解。
 * <p>用于标注字段或方法参数，指定该 Collection 类型的元素是否允许为空。</p>
 *
 * <pre>
 * 示例用法：
 * {@code
 * public class Example {
 *     @BizXCheckCollectionIsEmpty(
 *         isEmpty = false,
 *         // 异常Code码和信息定义
 *         error = @BizXCheckErrorMessage(code = 1000, message = "Collection cannot be empty")
 *     )
 *     private List<String> names;
 * }
 * }
 * </pre>
 *
 * @see BizXCheckErrorMessage
 * @since 1.0.1
 * @version 1.0.0
 * @author francis
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizXCheckCollectionIsEmpty {

    /**
     * 指定集合是否可以为空。
     *
     * @return true 表示集合可以为空，false 表示集合不能为空
     */
    boolean isEmpty() default true;

    /**
     * 自定义异常信息。
     *
     * @return 异常信息
     */
    BizXCheckErrorMessage error() default @BizXCheckErrorMessage(
            code = VerificationErrorConstant.CheckCollectionIsEmptyError.CODE,
            message = VerificationErrorConstant.CheckCollectionIsEmptyError.MESSAGE);

}
