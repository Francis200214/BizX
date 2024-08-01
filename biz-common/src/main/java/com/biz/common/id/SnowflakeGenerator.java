package com.biz.common.id;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 雪花ID生成器，用于生成全局唯一且递增的ID。
 * 使用Snowflake算法，基于时间戳、机器ID和序列号生成ID。
 *
 * @author francis
 * @since 2023-07-09 09:49
 **/
public class SnowflakeGenerator {

    // 基准时间戳，即2021年1月1日0点，用于计算相对于此时间的毫秒数
    private static final long EPOCH = 1609459200000L;
    // 序列号占用的位数
    private static final int SEQUENCE_BITS = 12;
    // 机器ID占用的位数
    private static final int MACHINE_ID_BITS = 10;
    // 序列号的最大值
    private static final int MAX_SEQUENCE = (1 << SEQUENCE_BITS) - 1;
    // 机器ID的最大值
    private static final int MAX_MACHINE_ID = (1 << MACHINE_ID_BITS) - 1;

    // 基于基准时间的偏移量
    private final long twepoch = EPOCH;
    // 机器ID，用于区分不同的生成器实例
    private final int machineId;
    // 用于同步生成ID的锁
    private final Lock lock = new ReentrantLock();

    // 上次生成ID的时间戳
    private volatile long lastTimestamp = -1L;
    // 当前序列号
    private volatile int sequence = 0;

    /**
     * 默认机器ID，用于没有指定机器ID的情况
     */
    public static final int DEFAULT_MACHINE_ID = 0;

    /**
     * 构造函数，初始化SnowflakeGenerator。
     *
     * @param machineId 机器ID，用于区分不同的生成器实例。
     *                  必须在0到MAX_MACHINE_ID之间。
     */
    public SnowflakeGenerator(int machineId) {
        if (isMachineIdNotValid(machineId)) {
            throw new IllegalArgumentException("Invalid machine ID");
        }
        this.machineId = machineId;
    }

    /**
     * 生成全局唯一的ID。
     *
     * @return 生成的唯一ID。
     */
    public long generate() {
        lock.lock();
        try {
            long timestamp = System.currentTimeMillis();

            // 处理系统时间回拨的情况
            if (timestamp < lastTimestamp) {
                handleClockBackward(lastTimestamp);
            }

            // 当时间戳与上一次相同，递增序列号
            if (timestamp == lastTimestamp) {
                sequence = (sequence + 1) & MAX_SEQUENCE;
                // 如果序列号溢出，则等待下一个时间戳
                if (sequence == 0) {
                    timestamp = waitForNextMillis(lastTimestamp);
                }
            } else {
                // 重置序列号
                sequence = 0;
            }

            // 更新上一次生成ID的时间戳
            lastTimestamp = timestamp;

            // 构造ID：时间戳部分、机器ID部分和序列号部分
            return ((timestamp - twepoch) << (MACHINE_ID_BITS + SEQUENCE_BITS)) |
                    ((long) machineId << SEQUENCE_BITS) |
                    sequence;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检查机器ID是否不合法。
     *
     * @param machineId 机器ID
     * @return 机器ID是否不合法
     */
    public static boolean isMachineIdNotValid(int machineId) {
        return machineId < 0 || machineId > MAX_MACHINE_ID;
    }

    /**
     * 处理系统时间回拨的情况，抛出异常拒绝生成ID。
     *
     * @param lastTimestamp 上次生成ID的时间戳
     */
    private void handleClockBackward(long lastTimestamp) {
        throw new RuntimeException(String.format("Clock moved backward. Refusing to generate id for timestamp %d while last timestamp was %d",
                System.currentTimeMillis(), lastTimestamp));
    }

    /**
     * 等待直到下一个毫秒，避免系统时间回拨导致的问题。
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 等待后的当前时间戳
     */
    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
