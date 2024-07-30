package com.biz.common.concurrent;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 提供线程池及相关工具方法的类。
 */
@Slf4j
public final class ExecutorsUtils {

    // 核心线程数，等于可用处理器数量
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    // 最大线程数，为核心线程数的两倍
    private static final int MAX_SIZE = CORE_SIZE * 2;
    // 线程保持活跃时间，单位秒
    private static final long ALIVE_TIME = 60L;
    // 时间单位为毫秒
    private static final TimeUnit MILLISECONDS = TimeUnit.MILLISECONDS;
    // 队列大小根据JVM最大内存计算得到，最大值为10000
    private static final int QUEUE_SIZE = calculateQueueSize();
    // 使用有界队列，避免过度膨胀
    private static final LinkedBlockingQueue<Runnable> LINKED_BLOCKING_QUEUE = new LinkedBlockingQueue<>(QUEUE_SIZE);

    // 私有构造方法，防止实例化
    private ExecutorsUtils() {
    }

    // 计算队列大小，根据JVM最大内存平均分配每个任务的空间
    private static int calculateQueueSize() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 以每 1MB 对应 1 个任务的策略
        int queueSize = (int) (maxMemory / (1024 * 1024) / 2);
        // 限制最大队列长度为 10000
        return Math.min(queueSize, 10000);
    }

    /**
     * 创建一个ScheduledFuture任务。
     *
     * @param task 要执行的任务
     * @param millisecond 任务延迟时间，单位毫秒
     * @return ScheduledFuture任务的未来结果
     */
    public static ScheduledFuture<?> buildScheduledFuture(Runnable task, long millisecond) {
        return buildScheduledExecutorService().schedule(task, millisecond, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取一个ScheduledExecutorService实例。
     *
     * @return ScheduledExecutorService实例
     */
    public static ScheduledExecutorService buildScheduledExecutorService() {
        return buildScheduledExecutorService(CORE_SIZE);
    }

    /**
     * 根据给定的核心线程数获取一个ScheduledExecutorService实例。
     *
     * @param coreSize 核心线程数
     * @return ScheduledExecutorService实例
     */
    public static ScheduledExecutorService buildScheduledExecutorService(int coreSize) {
        return Executors.newScheduledThreadPool(Math.max(coreSize, 1), new CustomThreadFactory());
    }

    /**
     * 创建一个ThreadPoolExecutor实例。
     *
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor() {
        return buildThreadPoolExecutor(CORE_SIZE, MAX_SIZE, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 创建一个具有指定核心池大小的ThreadPoolExecutor实例。
     *
     * @param corePoolSize 核心池大小
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize) {
        return buildThreadPoolExecutor(corePoolSize, corePoolSize + CORE_SIZE, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 创建一个具有指定核心池大小和最大池大小的ThreadPoolExecutor实例。
     *
     * @param corePoolSize 核心池大小
     * @param maximumPoolSize 最大池大小
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        return buildThreadPoolExecutor(corePoolSize, maximumPoolSize, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 创建一个具有指定核心池大小、最大池大小和线程保持活跃时间的ThreadPoolExecutor实例。
     *
     * @param corePoolSize 核心池大小
     * @param maximumPoolSize 最大池大小
     * @param keepAliveTime 线程保持活跃时间
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        return buildThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 创建一个完全自定义的ThreadPoolExecutor实例。
     *
     * @param corePoolSize 核心池大小
     * @param maximumPoolSize 最大池大小
     * @param keepAliveTime 线程保持活跃时间
     * @param workQueue 工作队列
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(
                Math.max(corePoolSize, 1),
                maximumPoolSize,
                keepAliveTime,
                MILLISECONDS,
                workQueue,
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler()
        );
    }

    /**
     * 关闭给定的ThreadPoolExecutor。
     *
     * @param executor 要关闭的线程池
     */
    public static void shutdownThreadPool(ThreadPoolExecutor executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        log.error("线程池没有被终止!!!");
                    }
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("线程池关闭过程中线程被中断", ie);
            }
        }
    }

    /**
     * 关闭给定的ScheduledExecutorService。
     *
     * @param executor 要关闭的计划任务执行器
     */
    public static void shutdownScheduledExecutor(ScheduledExecutorService executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        log.info("Scheduled executor did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("Scheduled executor关闭过程中线程被中断", ie);
            }
        }
    }

    /**
     * 自定义线程工厂，用于创建线程池中的线程。
     */
    private static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        /**
         * 创建并返回一个新线程。
         *
         * @param r 线程要执行的任务
         * @return 新创建的线程
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, "pool-thread-" + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false); // 确保线程是非守护线程
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY); // 确保线程优先级正常
            }
            return t;
        }
    }

    /**
     * 自定义拒绝策略，用于处理线程池无法接受的新任务。
     */
    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        /**
         * 当尝试提交的任务被拒绝时调用。
         *
         * @param r 被拒绝的任务
         * @param executor 线程池执行器
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.warn("Task rejected from thread pool: {}", r.toString());
        }
    }

}
