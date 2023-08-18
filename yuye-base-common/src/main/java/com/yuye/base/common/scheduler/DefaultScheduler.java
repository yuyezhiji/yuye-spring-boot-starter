package com.yuye.base.common.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.MDC;

import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 调度器
 * @author yuye
 */
@Slf4j
public class DefaultScheduler {

    private ScheduledThreadPoolExecutor executor;
    private final AtomicInteger schedulerThreadId = new AtomicInteger(0);
    private final AtomicBoolean shutdown = new AtomicBoolean(true);

    public DefaultScheduler(String threadNamePrefix) {
        // 这个支持调度的线程池组件，默认的线程数量是cpu cores * 2
        this(threadNamePrefix, Runtime.getRuntime().availableProcessors() * 2, true);
    }

    public DefaultScheduler(String threadNamePrefix, int threads) {
        this(threadNamePrefix, threads, true);
    }

    public DefaultScheduler(String threadNamePrefix, int threads, boolean daemon) {
        // shutdown atomic boolean，他会把你的支持调度的线程池，从关闭状态修改为false
        if (shutdown.compareAndSet(true, false)) {
            // 线程池是如何来进行构建的
            executor = new ScheduledThreadPoolExecutor(threads,
                    r -> new DefaultThread(threadNamePrefix + schedulerThreadId.getAndIncrement(), r, daemon));
            // 如果你要是对调度线程池进行shutdown关闭了以后，已经存在的周期性调度执行的任务是否还要继续
            executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
            // 如果调度线程池shutdown了，已经设置的一些延迟任务是否还要去执行
            executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        }
    }

    /**
     * 调度任务
     * 不要延迟调度执行这个任务，就直接拿一个线程来执行就可以了
     * @param name 任务名称
     * @param r    任务
     */
    public void scheduleOnce(String name, Runnable r) {
        scheduleOnce(name, r, 0);
    }

    /**
     * 调度任务
     *
     * @param name  任务名称
     * @param r     任务
     * @param delay 延迟
     */
    public void scheduleOnce(String name, Runnable r, long delay) {
        // 你可以以延迟任务来调度，给他一个任务，但是要求必须要延迟一段时间再进行调度执行
        schedule(name, r, delay, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 调度任务
     *
     * 首次任务调度执行需要延迟一定的时间，而且后续他会周期性的调度执行
     *
     *  @param name     任务名称
     * @param r        任务
     * @param delay    延迟执行时间
     * @param period   调度周期
     * @param timeUnit 时间单位
     */
    public ScheduledFuture<?> schedule(String name, Runnable r, long delay, long period, TimeUnit timeUnit) {
        if (log.isDebugEnabled()) {
            log.debug("Scheduling task {} with initial delay {} {} and period {} {}}.", name, delay, timeUnit, period, timeUnit);
        }
        // 会对我们的提交过来的runnable任务进行一个封装
        Runnable delegate = () -> {
            try {
                if (log.isTraceEnabled()) {
                    log.trace("Beginning execution of scheduled task {}.", name);
                }

                // 放入线程本地变量里的loggerid
                String loggerId = DigestUtils.md5Hex("" + System.nanoTime() + new Random().nextInt());
                // 他会获取到当前线程绑定的threadlocal里的map，然后把你的keyvalue对放入到map里去
                MDC.put("logger_id", loggerId);

                r.run();
            } catch (Throwable e) {
                log.error("Uncaught exception in scheduled task {} :", name, e);
            } finally {
                if (log.isTraceEnabled()) {
                    log.trace("Completed execution of scheduled task {}.", name);
                }
                // 把你的日志id去进行剔除和删除
                MDC.remove("logger_id");
            }
        };
        if (shutdown.get()) {
            return null;
        }
        // period大于0，说明你提交的任务是要周期性的运行
        if (period > 0) {
            return executor.scheduleWithFixedDelay(delegate, delay, period, timeUnit);
        }
        // period如果是=0，他不是周期性的运行的，他是运行一次，但是运行这一次的时候会延迟一会儿
        else {
           return  executor.schedule(delegate, delay, timeUnit);
        }
    }

    /**
     * 优雅停止
     */
    public void shutdown() {
        if (shutdown.compareAndSet(false, true)) {
            log.info("Shutdown DefaultScheduler.");
            executor.shutdown();
        }
    }
}
