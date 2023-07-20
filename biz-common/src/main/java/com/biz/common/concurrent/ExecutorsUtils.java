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
    private static final LinkedBlockingQueue<Runnable> LINKED_BLOCKING_QUEUE = new LinkedBlockingQueue<>(10000);


    /**
     * 过多少毫秒后执行
     *
     * @param task
     * @param millisecond
     * @return
     */
    public static ScheduledFuture<?> buildScheduledFuture(Runnable task, long millisecond) {
        return buildScheduledExecutorService().schedule(task, millisecond, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取定时任务线程池
     *
     * @return
     */
    public static ScheduledExecutorService buildScheduledExecutorService() {
        return getScheduledExecutorService(CORE_SIZE);
    }

    /**
     * 获取定时任务线程池
     *
     * @param coreSize 核心线程数
     * @return
     */
    public static ScheduledExecutorService buildScheduledExecutorService(int coreSize) {
        return getScheduledExecutorService(coreSize);
    }

    /**
     * 获取线程池
     *
     * @return
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor() {
        return getThreadPoolExecutor(CORE_SIZE, MAX_SIZE, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize 核心线程数（最小线程数）
     * @return
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize) {
        return getThreadPoolExecutor(corePoolSize, MAX_SIZE, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize 核心线程数（最小线程数）
     * @param maximumPoolSize 最大线程数
     * @return
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, ALIVE_TIME, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize 核心线程数（最小线程数）
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 空闲线程存活时间
     * @return
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, LINKED_BLOCKING_QUEUE);
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize 核心线程数（最小线程数）
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 空闲线程存活时间
     * @param workQueue 任务队列【建议定义任务队列的长度，防止OOM异常】
     * @return
     */
    public static ThreadPoolExecutor buildThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return getThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, workQueue);
    }



    private static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, MILLISECONDS, workQueue) {
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
