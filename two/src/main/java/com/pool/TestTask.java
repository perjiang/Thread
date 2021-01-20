package com.pool;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 各种任务提交机制
 */
@Slf4j(topic = "c.TestTask")
public class TestTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
       String result  = pool.invokeAny(Arrays.asList(
                ()->{
                    log.debug("begin 1");
                    Thread.sleep(1000);
                    log.debug("end 1");
                    return "1";
                },
                () -> {
                    log.debug("begin 2");
                    Thread.sleep(2000);
                    log.debug("end 2");
                    return "2";
                },
                () -> {
                    log.debug("begin 3");
                    Thread.sleep(3000);
                    log.debug("end 3");
                    return "3";
                }
        ));

//       log.debug("{}",result);

//        Future<Boolean> task = pool.submit(() -> {
//            log.debug("task");
//            int i = 1 / 0;
//            return true;
//        });
//        log.debug("{}",task.get());

    }

    private static List<Future<String>> method2(ExecutorService pool) throws InterruptedException {
        return pool.invokeAll(Arrays.asList(
                () -> {
                    log.debug("begin 1");
                    Thread.sleep(1000);
                    log.debug("end");
                    return "1";

                },
                () -> {
                    log.debug("begin 2");
                    Thread.sleep(2000);
                    log.debug("end 2");
                    return "2";
                },
                () -> {
                    log.debug("begin 3");
                    Thread.sleep(3000);
                    log.debug("end 3");
                    return "3";
                }
        ));
    }

    private static Future<String> method1(ExecutorService pool) {
        return pool.submit(() -> {
               log.debug("start 1");
               Thread.sleep(1);
               log.debug("end 1");
                return "yes";
            });
    }
}
