package com.biz.common.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 用于定时执行任务的工具类。
 * <p>提供了提交、取消和重置任务的能力，并封装了 {@link ScheduledExecutorService} 的使用。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     BizScheduledFuture bizScheduledFuture = BizScheduledFuture.builder()
 *         .runnable(() -> System.out.println("Task executed"))
 *         .time(5000)
 *         .build();
 *     bizScheduledFuture.submit();
 * }
 * </pre>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
@Slf4j
public final class BizScheduledFuture {

    /**
     * 用于执行任务的 {@link ScheduledExecutorService} 实例。
     */
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 要执行的任务。
     */
    private final Runnable runnable;

    /**
     * 任务执行的延迟时间，单位为毫秒。
     */
    private final long time;

    /**
     * 当前计划的任务。
     */
    private ScheduledFuture<?> scheduledFuture;

    /**
     * 锁，用于确保任务提交、取消和重置操作的线程安全性。
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 构造函数初始化 {@code BizScheduledFuture}。
     *
     * @param scheduledExecutorService 用于执行任务的 {@link ScheduledExecutorService}，如果为null，则创建一个新的。
     * @param runnable 要执行的任务，不能为空。
     * @param time 任务执行的延迟时间，单位为毫秒。
     */
    public BizScheduledFuture(ScheduledExecutorService scheduledExecutorService, Runnable runnable, long time) {
        this.scheduledExecutorService = scheduledExecutorService == null
                ? ExecutorsUtils.buildScheduledExecutorService(10)
                : scheduledExecutorService;
        this.runnable = Objects.requireNonNull(runnable, "Runnable cannot be null");
        this.time = time;
    }

    /**
     * 提交任务给 {@link ScheduledExecutorService} 执行。
     * <p>在提交之前，会尝试取消任何当前计划的任务，以避免资源浪费。</p>
     */
    public void submit() {
        lock.lock();
        try {
            cancel(); // 先尝试取消当前任务，避免资源浪费
            scheduledFuture = this.scheduledExecutorService.schedule(() -> {
                try {
                    runnable.run();
                } catch (Exception e) {
                    log.error("任务执行过程中出现异常：", e);
                }
            }, time, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("提交计划任务时出现异常错误：", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取消当前计划的任务。
     * <p>如果任务正在执行，允许它完成。</p>
     */
    public void cancel() {
        lock.lock();
        try {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false); // 使用false以允许正在运行的任务完成
                scheduledFuture = null;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 重置并重新提交任务。
     * <p>先取消当前任务，然后以新的延迟时间提交任务。</p>
     */
    public void resetDied(long time) {
        lock.lock();
        try {
            cancel();
            submit(); // 重新提交任务时，不需要再次传递参数，因为它们在构造函数中已经被设定了
        } finally {
            lock.unlock();
        }
    }

    /**
     * 关闭 {@link ScheduledExecutorService}。
     * <p>应在不再需要执行任务时调用，以释放资源。</p>
     */
    public void shutdownExecutorService() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    /**
     * 获取一个构建器，用于逐步构建 {@code BizScheduledFuture} 实例。
     *
     * @return {@link ScheduledFutureBuilder} 构建器实例。
     */
    public static ScheduledFutureBuilder builder() {
        return new ScheduledFutureBuilder();
    }

    /**
     * 构建器类，用于简化 {@code BizScheduledFuture} 的创建。
     */
    public static class ScheduledFutureBuilder {

        private Runnable runnable;
        private long time;
        private ScheduledExecutorService scheduledExecutorService = ExecutorsUtils.buildScheduledExecutorService(10); // 提供默认值

        private ScheduledFutureBuilder() {
        }

        /**
         * 设置要执行的任务。
         *
         * @param runnable 要执行的任务，不能为空。
         * @return {@link ScheduledFutureBuilder} 以便进行链式调用。
         */
        public ScheduledFutureBuilder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        /**
         * 设置任务执行的延迟时间。
         *
         * @param time 任务执行的延迟时间，单位为毫秒。
         * @return {@link ScheduledFutureBuilder} 以便进行链式调用。
         */
        public ScheduledFutureBuilder time(long time) {
            this.time = time;
            return this;
        }

        /**
         * 设置用于执行任务的 {@link ScheduledExecutorService}。
         *
         * @param scheduledExecutorService 用于执行任务的 {@link ScheduledExecutorService}。
         * @return {@link ScheduledFutureBuilder} 以便进行链式调用。
         */
        public ScheduledFutureBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
            return this;
        }

        /**
         * 使用当前设置构建 {@code BizScheduledFuture} 实例。
         *
         * @return {@link BizScheduledFuture} 构建的实例。
         */
        public BizScheduledFuture build() {
            return new BizScheduledFuture(scheduledExecutorService, runnable, time);
        }
    }
}
