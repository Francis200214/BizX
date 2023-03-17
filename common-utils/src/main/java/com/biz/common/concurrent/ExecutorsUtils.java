package com.biz.common.concurrent;


import java.util.concurrent.*;

/**
 * 线程池工具类
 */
public final class ExecutorsUtils {

    private static final int CORE_SIZE = 10;
    private static final int MAX_SIZE = 50;
    private static final long ALIVE_TIME = 0L;
    private static final TimeUnit MILLISECONDS = TimeUnit.MILLISECONDS;
    private static final LinkedBlockingQueue<Runnable> LINKED_BLOCKING_QUEUE = new LinkedBlockingQueue<>();

    public static ScheduledExecutorService buildScheduledExecutorService() {
        return getScheduledExecutorService(CORE_SIZE);
    }

    public static ScheduledExecutorService buildScheduledExecutorService(int coreSize) {
        return getScheduledExecutorService(coreSize);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor() {
        return getThreadPoolExecutor(-1, -1, -1, null);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize) {
        return getThreadPoolExecutor(corePoolSize, -1, -1, null);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, -1, null);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, null);
    }

    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, workQueue);
    }

    private static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(
                corePoolSize <= 0 ? CORE_SIZE : corePoolSize,
                maximumPoolSize <= 0 ? MAX_SIZE : maximumPoolSize,
                keepAliveTime <= -1 ? ALIVE_TIME : keepAliveTime,
                MILLISECONDS,
                workQueue == null ? LINKED_BLOCKING_QUEUE : workQueue) {
            @Override
            public void execute(Runnable command) {
                synchronized (this) {
                    super.execute(command);
                }
            }
        };
    }

    private static ScheduledExecutorService getScheduledExecutorService(int coreSize) {
        return Executors.newScheduledThreadPool(coreSize <= -1 ? CORE_SIZE : coreSize);
    }

}
