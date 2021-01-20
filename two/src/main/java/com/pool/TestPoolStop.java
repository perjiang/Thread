package com.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池停止方法
 */
@Slf4j(topic = "c.TestPoolStop")
public class TestPoolStop {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        Future<Integer> task1 = pool.submit(() -> {
            log.debug("task1  begin");
            Thread.sleep(1000);
            log.debug("task1 end");
            return 1;
        });

        Future<Integer> task2 = pool.submit(() -> {
            log.debug("task2  begin");
            Thread.sleep(1000);
            log.debug("task2 end");
            return 2;
        });

        Future<Integer> task3 = pool.submit(() -> {
            log.debug("task3  begin");
            Thread.sleep(1000);
            log.debug("task3 end");
            return 3;
        });
        Future<Integer> task4 = pool.submit(() -> {
            log.debug("task4  begin");
            Thread.sleep(1000);
            log.debug("task4 end");
            return 4;
        });
        Future<Integer> task5 = pool.submit(() -> {
            log.debug("task5  begin");
            Thread.sleep(1000);
            log.debug("task5 end");
            return 5;
        });
        log.debug("主线程shutdown线程池");
        List<Runnable> runnables = pool.shutdownNow();
        ExecutorService service = Executors.newFixedThreadPool(3);
        runnables.forEach((x)->{
            log.debug("第二个池开始执行");
            service.submit(x);
        });


//        service.shutdown();
    }
}
