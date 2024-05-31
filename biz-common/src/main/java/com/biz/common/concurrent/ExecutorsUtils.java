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

    private static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group;

        CustomThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, "pool-thread-" + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.warn("Task rejected from thread pool: {}", r.toString());
        }
    }

    public static void shutdownThreadPool(ThreadPoolExecutor executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS) {
                        log.info("Thread pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

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
}
