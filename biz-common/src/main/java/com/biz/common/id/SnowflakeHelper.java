package com.biz.common.id;

/**
 * 雪花ID增强器
 *
 * @author francis
 * @create: 2023-07-09 09:52
 **/
public class SnowflakeHelper {

    private final SnowflakeGenerator generator;

    public SnowflakeHelper(int machineId) {
        this.generator = new SnowflakeGenerator(machineId);
    }


    public String create() {
        return String.valueOf(generator.generate());
    }

    public static String createDefault() {
        return String.valueOf(new SnowflakeGenerator(SnowflakeGenerator.DEFAULT_MACHINE_ID).generate());
    }


    public static SnowflakeBuilder builder() {
        return new SnowflakeBuilder();
    }

    public static class SnowflakeBuilder {
        private int machineId;

        public SnowflakeBuilder machineId(int machineId) {
            this.machineId = machineId;
            return this;
        }

        public SnowflakeHelper build() {
            return new SnowflakeHelper(machineId);
        }

    }


}
