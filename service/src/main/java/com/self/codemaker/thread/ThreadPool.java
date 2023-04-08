package com.self.codemaker.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
@Slf4j
public class ThreadPool {


    private static final Random RANDOM = new Random();

    static {
        RANDOM.setSeed(System.currentTimeMillis());
    }

    private ThreadPool() {
    }

    public final static ThreadPoolExecutor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(
            10, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(0x3e8),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("poolExecutor" + RANDOM.nextInt(1000));
                return thread;
            }, (r, executor1) -> log.error("poolExecutor reject: {}", r.toString()));

}