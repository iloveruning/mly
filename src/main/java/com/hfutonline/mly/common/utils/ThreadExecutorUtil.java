package com.hfutonline.mly.common.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author chenliangliang
 * @date 2018/2/23
 */
public class ThreadExecutorUtil {

    private static ThreadPoolTaskExecutor executor;

    public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {

        if (executor == null) {
            synchronized (ThreadExecutorUtil.class) {
                if (executor != null) {
                    return executor;
                }
                executor = SpringContextHolder.getBean("myExecutor");
            }
        }
        return executor;
    }

    public static void execute(Runnable task) {
        getThreadPoolTaskExecutor().execute(task);
    }

    public static void execute(Runnable task, long timeOut) {
        getThreadPoolTaskExecutor().execute(task, timeOut);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return getThreadPoolTaskExecutor().submit(task);
    }

}
