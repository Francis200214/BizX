package com.biz.common.concurrent;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具类
 */
@Slf4j
public final class ExecutorsUtils {

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_SIZE = CORE_SIZE * 2;
    private static final long ALIVE_TIME = 60L;
    private static final TimeUnit MILLISECONDS = TimeUnit.MILLISECONDS;
    private static final int QUEUE_SIZE = calculateQueueSize();
    private static final LinkedBlockingQueue<Runnable> LINKED_BLOCKING_QUEUE = new LinkedBlockingQueue<>(QUEUE_SIZE);

    private ExecutorsUtils() {
    }

    private static int calculateQueueSize() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 以每 1MB 对应 1 个任务的策略
        int queueSize = (int) (maxMemory / (1024 * 1024) / 2);
        // 限制最大队列长度为 10000
        return Math.min(queueSize, 10000);
    }

    public static ScheduledFuture<?> buildScheduledFuture(Runnable task, long millisecond) {
        return buildScheduledExecutorService().schedule(task, millisecond, TimeUnit.MILLISECONDS);
    }

    public static ScheduledExecutorService buildScheduledExecutorService() {
        return getScheduledExecutorService(CORE_SIZE);
    }

    public static ScheduledExecutorService buildScheduledExecutorService(int coreSize) {
        return getScheduledExecutorService(coreSize);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor() {
        return getThreadPoolExecutor(CORE_SIZE, MAX_SIZE, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize) {
        return getThreadPoolExecutor(corePoolSize, corePoolSize + CORE_SIZE, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, LINKED_BLOCKING_QUEUE);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, workQueue);
    }


    /**
     * 关闭线程池
     * 关闭，并且暂停线程池中所有任务
     *
     * @param executor 线程池
     */
    public static void shutdownThreadPool(ThreadPoolExecutor executor) {
        if (executor != null) {
            // 禁止新的任务提交，同时执行已提交的任务
            executor.shutdown();
            try {
                // 等待现有任务完成
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    // 取消正在执行的任务并尝试停止所有活动的任务
                    executor.shutdownNow();
                    // 再次等待这些任务终止
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        log.error("线程池没有被终止!!!");
                    }
                }
            } catch (InterruptedException ie) {
                // 重新中断当前线程
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 关闭线程池
     * 关闭，并且暂停线程池中所有任务
     *
     * @param executor 线程池
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
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }


    private static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, MILLISECONDS, workQueue, new CustomThreadFactory(), new CustomRejectedExecutionHandler()) {
            @Override
            public void execute(Runnable command) {
                log.info("Task submitted to thread pool");
                super.execute(command);
            }
        };
    }

    private static ScheduledExecutorService getScheduledExecutorService(int coreSize) {
        return Executors.newScheduledThreadPool(coreSize <= -1 ? CORE_SIZE : coreSize, new CustomThreadFactory());
    }


    /**
     * 用于自定义线程命名、设置线程属性、线程组管理
     */
    private static class CustomThreadFactory implements ThreadFactory {
        // 线程编号
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group;

        CustomThreadFactory() {
            // 获取安全管理器，并从中获取线程组。如果没有安全管理器，则使用当前线程的线程组
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, "pool-thread-" + threadNumber.getAndIncrement(), 0);
            // 设置线程为非守护线程
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            // 设置线程的优先级为正常优先级
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 拒绝策略类，用于处理当线程池任务被拒绝时的情况
     */
    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.warn("Task rejected from thread pool: {}", r.toString());
        }
    }

}
