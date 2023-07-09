package com.biz.common.id;

/**
 * 雪花Id生成
 *
 * @author francis
 * @create: 2023-07-09 09:49
 **/
public class SnowflakeGenerator {

    // 自定义起始时间戳（2021-01-01 00:00:00）
    private static final long EPOCH = 1609459200000L;
    private static final int SEQUENCE_BITS = 12;
    private static final int MACHINE_ID_BITS = 10;
    private static final int MAX_SEQUENCE = (1 << SEQUENCE_BITS) - 1;
    private static final int MAX_MACHINE_ID = (1 << MACHINE_ID_BITS) - 1;

    private long lastTimestamp = -1L;
    private int sequence = 0;
    private final int machineId;

    /**
     * 默认机器ID
     */
    public static final int DEFAULT_MACHINE_ID = 0;

    public SnowflakeGenerator(int machineId) {
        if (machineId < 0 || machineId > MAX_MACHINE_ID) {
            throw new IllegalArgumentException("Invalid machine ID");
        }
        this.machineId = machineId;
    }


    public synchronized long generate() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Invalid system clock");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;

            if (sequence == 0) {
                timestamp = nextTimestamp(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << (MACHINE_ID_BITS + SEQUENCE_BITS)) |
                ((long) machineId << SEQUENCE_BITS) |
                sequence;
    }

    private long nextTimestamp(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();

        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }

        return timestamp;
    }

}
