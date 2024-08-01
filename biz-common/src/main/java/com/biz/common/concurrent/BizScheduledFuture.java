package com.biz.common.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 用于定时执行任务的工具类
 * 提供了提交、取消和重置任务的能力，并封装了ScheduledExecutorService的使用。
 *
 * @author francis
 * @since 2023-04-23 18:17
 **/
@Slf4j
public class BizScheduledFuture {

    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 执行的事件
     */
    private final Runnable runnable;
    /**
     * 多久执行
     */
    private final long time;
    /**
     * 执行
     */
    private ScheduledFuture<?> scheduledFuture;

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 构造函数初始化BizScheduledFuture。
     *
     * @param scheduledExecutorService 用于执行任务的ScheduledExecutorService，如果为null，则创建一个新的。
     * @param runnable 要执行的任务。
     * @param time 任务执行的延迟时间。
     */
    public BizScheduledFuture(ScheduledExecutorService scheduledExecutorService, Runnable runnable, long time) {
        this.scheduledExecutorService = scheduledExecutorService == null
                ? ExecutorsUtils.buildScheduledExecutorService(10)
                : scheduledExecutorService;
        this.runnable = Objects.requireNonNull(runnable, "Runnable cannot be null");
        this.time = time;
    }

    /**
     * 提交任务给ScheduledExecutorService执行。
     * 在提交之前，会尝试取消任何当前计划的任务，以避免资源浪费。
     */
    public void submit() {
        lock.lock();
        try {
            cancel(); // 先尝试取消当前任务，避免资源浪费
            scheduledFuture = this.scheduledExecutorService.schedule(() -> {
                try {
                    runnable.run();
                } catch (Exception e) {
                    // 日志记录或其他异常处理机制
                }
            }, time, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("取消计划任务的时候出现了异常错误：", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取消当前计划的任务。
     * 如果任务正在执行，允许它完成。
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
     * 先取消当前任务，然后以新的延迟时间提交任务。
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
     * 关闭ScheduledExecutorService。
     * 应在不再需要执行任务时调用，以释放资源。
     */
    // 提供一个适当的关闭方法来关闭ExecutorService，可能需要在适当的时候调用它
    public void shutdownExecutorService() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    /**
     * 获取一个构建器，用于逐步构建BizScheduledFuture实例。
     *
     * @return ScheduledFutureBuilder 构建器实例。
     */
    public static ScheduledFutureBuilder builder() {
        return new ScheduledFutureBuilder();
    }

    /**
     * 构建器类，用于简化BizScheduledFuture的创建。
     */
    /**
     * 内置 Builder
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
         * @param runnable 要执行的任务。
         * @return ScheduledFutureBuilder 以便进行链式调用。
         */
        public ScheduledFutureBuilder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        /**
         * 设置任务执行的延迟时间。
         *
         * @param time 任务执行的延迟时间。
         * @return ScheduledFutureBuilder 以便进行链式调用。
         */
        public ScheduledFutureBuilder time(long time) {
            this.time = time;
            return this;
        }

        /**
         * 设置用于执行任务的ScheduledExecutorService。
         *
         * @param scheduledExecutorService 用于执行任务的ScheduledExecutorService。
         * @return ScheduledFutureBuilder 以便进行链式调用。
         */
        public ScheduledFutureBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
            return this;
        }

        /**
         * 使用当前设置构建BizScheduledFuture实例。
         *
         * @return BizScheduledFuture 构建的实例。
         */
        public BizScheduledFuture build() {
            return new BizScheduledFuture(scheduledExecutorService, runnable, time);
        }
    }
}
