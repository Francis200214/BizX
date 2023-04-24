package com.biz.common.concurrent;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 定时执行某件事
 *
 * @author francis
 * @create: 2023-04-23 18:17
 **/
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

    private final ReentrantLock lock = new ReentrantLock(true);

    public BizScheduledFuture(ScheduledExecutorService scheduledExecutorService, Runnable runnable, long time) {
        this.scheduledExecutorService = scheduledExecutorService == null ? ExecutorsUtils.buildScheduledExecutorService(10) : scheduledExecutorService;
        this.runnable = runnable;
        this.time = time;
    }

    public void submit() {
        submit(this.runnable, this.time);
    }


    public void cancel() {
        if (scheduledFuture == null) {
            return;
        }
        lock.lock();
        try {
            if (scheduledFuture == null) {
                return;
            }
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        } finally {
            lock.unlock();
        }
    }


    public void resetDied(long time) {
        lock.lock();
        try {
            cancel();
            submit(runnable, time);
        } finally {
            lock.unlock();
        }
    }


    private void submit(Runnable runnable, long time) {
        Objects.requireNonNull(runnable, "This is runnable is not null");
        lock.lock();
        try {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            scheduledFuture = this.scheduledExecutorService.schedule(() -> {
                lock.lock();
                try {
                    runnable.run();
                } finally {
                    lock.unlock();
                }
            }, time, TimeUnit.MILLISECONDS);
        } finally {
            lock.unlock();
        }
    }


    public static ScheduledFutureBuilder builder() {
        return new ScheduledFutureBuilder();
    }

    /**
     * 内置 Builder
     */
    public static class ScheduledFutureBuilder {

        private Runnable runnable;
        private long time;
        private ScheduledExecutorService scheduledExecutorService;

        private ScheduledFutureBuilder() {
        }

        public ScheduledFutureBuilder runnable(Runnable runnable) {
            this.runnable = runnable;
            return this;
        }

        public ScheduledFutureBuilder time(long time) {
            this.time = time;
            return this;
        }

        public ScheduledFutureBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
            return this;
        }

        public BizScheduledFuture build() {
            return new BizScheduledFuture(scheduledExecutorService, runnable, time);
        }
    }


}
