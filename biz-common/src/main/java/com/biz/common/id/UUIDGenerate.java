package com.biz.common.id;

import java.util.UUID;

/**
 * UUID 生成工具类。
 * <p>该类提供了一个静态方法用于生成唯一的UUID字符串。</p>
 *
 * <pre>
 * 示例使用：
 * {@code
 * String uuid = UUIDGenerate.generate();
 * }
 * </pre>
 *
 * <p>该类是不可实例化的，因为它只包含静态方法。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public final class UUIDGenerate {

    /**
     * 生成一个唯一的UUID字符串。
     *
     * @return 生成的UUID字符串
     */
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
