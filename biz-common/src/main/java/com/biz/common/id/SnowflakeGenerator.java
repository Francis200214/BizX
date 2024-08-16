package com.biz.common.id;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 雪花ID生成器，用于生成全局唯一且递增的ID。
 *
 * <p>该生成器基于Snowflake算法实现，通过时间戳、机器ID和序列号来生成唯一ID。
 * ID由以下部分组成：
 * <ul>
 *     <li>时间戳部分：基于自定义的基准时间（2021年1月1日0点）以来的毫秒数。</li>
 *     <li>机器ID部分：用于区分不同的生成器实例，避免ID冲突。</li>
 *     <li>序列号部分：用于同一毫秒内生成多个ID，保证ID的唯一性和递增性。</li>
 * </ul>
 * </p>
 *
 * <p>通过此类，您可以生成全球唯一且有序的ID，非常适合分布式系统中的唯一标识符生成。</p>
 *
 * <pre>
 * 示例使用：
 * {@code
 * SnowflakeGenerator generator = new SnowflakeGenerator(SnowflakeGenerator.DEFAULT_MACHINE_ID);
 * long id = generator.generate();
 * }
 * </pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
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
     *                  必须在0到{@link #MAX_MACHINE_ID}之间。
     * @throws IllegalArgumentException 如果机器ID超出允许范围，则抛出该异常
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
     * <p>该方法是线程安全的，能够确保在高并发环境下生成唯一且递增的ID。</p>
     *
     * @return 生成的唯一ID
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
     * @return 如果机器ID不在合法范围内，返回true，否则返回false
     */
    public static boolean isMachineIdNotValid(int machineId) {
        return machineId < 0 || machineId > MAX_MACHINE_ID;
    }

    /**
     * 处理系统时间回拨的情况，抛出异常拒绝生成ID。
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @throws RuntimeException 当检测到系统时间回拨时抛出此异常
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
