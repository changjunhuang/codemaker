package com.self.codemaker.thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @desc:
 * @author: huangchangjun
 * @date: 2023/4/8
 */
@Slf4j
@Service
public class TaskManager implements InitializingBean {

    private ExecutorService executorService;

    private static final String THREAD_POOL_NAME = "major_task_thread_pool";

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 获取cpu内核数
         */
        int availProcessors = Runtime.getRuntime().availableProcessors();
        executorService = ThreadPoolManager.createThreadPoolExecutor(THREAD_POOL_NAME, availProcessors * 2, availProcessors * 6, 1000);
    }

    public <R> List<R> submites(List<Supplier<R>> taskList, int timeout) throws Exception {
        //选择使用的线程池
        CompletableFuture<R>[] completableFutures = taskList.stream()
                .map(task -> CompletableFuture.supplyAsync(task, executorService))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> all = CompletableFuture.allOf(completableFutures);
        try {
            all.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            all.cancel(true);
            throw e;
        }

        // 返回值
        List<R> taskResultList = Lists.newArrayList();

        // 遍历请求结果
        for (CompletableFuture<R> completableFuture : completableFutures) {
            if (completableFuture.isDone()) {
                R result = null;
                try {
                    result = completableFuture.get();
                } catch (Exception e) {
                    log.error("completableFuture.get error", e);
                    completableFuture.cancel(true);
                }

                if (result != null) {
                    taskResultList.add(result);
                }
            }
        }
        return taskResultList;
    }

    public void execute(Supplier supplier) {
        executorService.execute(() -> {
            try {
                supplier.get();
            } catch (Exception e) {
                log.error(" taskmanager exe error ", e);
            }
        });
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

}
