package com.biz.common.id;

/**
 * 雪花ID生成器的帮助类，提供雪花ID的创建和配置。
 *
 * @author francis
 * @create: 2023-07-09 09:52
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
     * 默认的机器ID由SnowflakeGenerator的默认设置决定。
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
     * 提供一个构建器模式，用于逐步配置和创建SnowflakeHelper实例。
     *
     * @return SnowflakeBuilder实例，用于构建SnowflakeHelper。
     */
    public static SnowflakeBuilder builder() {
        return new SnowflakeBuilder();
    }

    /**
     * 雪花ID生成器的帮助类的构建器，用于设置机器ID并创建SnowflakeHelper实例。
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
         * 使用当前配置创建并返回SnowflakeHelper实例。
         *
         * @return 配置完成后创建的SnowflakeHelper实例。
         */
        public SnowflakeHelper build() {
            return new SnowflakeHelper(machineId);
        }

    }

}

