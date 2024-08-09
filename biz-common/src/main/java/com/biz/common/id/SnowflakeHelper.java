package com.biz.common.id;

/**
 * 雪花ID生成器的帮助类，提供便捷的雪花ID生成和配置方法。
 *
 * <p>该类封装了 {@link SnowflakeGenerator} 的实例化和ID生成逻辑，并提供了静态方法用于生成默认的雪花ID。</p>
 *
 * <pre>
 * 示例使用：
 * {@code
 * SnowflakeHelper helper = new SnowflakeHelper(1);
 * String id = helper.create();
 *
 * // 使用默认机器ID生成雪花ID
 * String defaultId = SnowflakeHelper.createDefault();
 *
 * // 使用构建器模式创建SnowflakeHelper
 * SnowflakeHelper customHelper = SnowflakeHelper.builder().machineId(2).build();
 * }
 * </pre>
 *
 * <p>该类还提供了构建器模式，通过 {@link SnowflakeBuilder} 可以逐步配置并创建 {@link SnowflakeHelper} 实例。</p>
 *
 * @see SnowflakeGenerator
 * @see SnowflakeBuilder
 *
 * @author francis
 * @since 2023-07-09 09:52
 **/
public class SnowflakeHelper {

    private final SnowflakeGenerator generator;

    /**
     * 使用指定的机器ID创建雪花ID生成器实例。
     *
     * @param machineId 机器ID，用于生成唯一的雪花ID。
     *                  机器ID必须在合法范围内，否则抛出IllegalArgumentException异常。
     */
    public SnowflakeHelper(int machineId) {
        // 合法性验证
        if (SnowflakeGenerator.isMachineIdNotValid(machineId)) {
            throw new IllegalArgumentException("机器ID不在合法范围内");
        }
        this.generator = new SnowflakeGenerator(machineId);
    }

    /**
     * 生成一个唯一的雪花ID，并以字符串形式返回。
     *
     * @return 生成的雪花ID的字符串表示。
     */
    public String create() {
        try {
            return String.valueOf(generator.generate());
        } catch (Exception e) {
            // 异常处理逻辑，例如记录日志或抛出自定义异常
            throw new RuntimeException("生成雪花ID时发生异常", e);
        }
    }

    /**
     * 生成一个默认的雪花ID，并以字符串形式返回。
     * 默认的机器ID由 {@link SnowflakeGenerator} 的默认设置决定。
     *
     * @return 生成的默认雪花ID的字符串表示。
     */
    public static String createDefault() {
        try {
            return String.valueOf(new SnowflakeGenerator(SnowflakeGenerator.DEFAULT_MACHINE_ID).generate());
        } catch (Exception e) {
            // 异常处理逻辑，与`create`方法类似
            throw new RuntimeException("生成默认雪花ID时发生异常", e);
        }
    }

    /**
     * 提供一个构建器模式，用于逐步配置和创建 {@link SnowflakeHelper} 实例。
     *
     * @return {@link SnowflakeBuilder} 实例，用于构建 {@link SnowflakeHelper}。
     */
    public static SnowflakeBuilder builder() {
        return new SnowflakeBuilder();
    }

    /**
     * 雪花ID生成器的帮助类的构建器，用于设置机器ID并创建 {@link SnowflakeHelper} 实例。
     */
    public static class SnowflakeBuilder {
        private int machineId;

        /**
         * 设置机器ID。
         *
         * @param machineId 机器ID，用于生成唯一的雪花ID。
         *                  机器ID必须在合法范围内且不能为负，否则抛出IllegalArgumentException异常。
         * @return 当前构建器实例，支持链式调用。
         */
        public SnowflakeBuilder machineId(int machineId) {
            // 与构造函数中的验证相同
            if (machineId < 0) {
                throw new IllegalArgumentException("机器ID不能为负数");
            }
            // 合法性验证
            if (SnowflakeGenerator.isMachineIdNotValid(machineId)) {
                throw new IllegalArgumentException("机器ID不在合法范围内");
            }
            this.machineId = machineId;
            return this;
        }

        /**
         * 使用当前配置创建并返回 {@link SnowflakeHelper} 实例。
         *
         * @return 配置完成后创建的 {@link SnowflakeHelper} 实例。
         */
        public SnowflakeHelper build() {
            return new SnowflakeHelper(machineId);
        }

    }

}
