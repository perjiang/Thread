package com.pool;

import lombok.extern.slf4j.Slf4j;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TestThreadPool")
public class TestThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int j = i;
            pool.execute(()->{
                log.debug("正在插入{}",j);
                list.add(j);
            });

        }
        TimeUnit.SECONDS.sleep(2);
        log.debug("{}",list);

        for (int i = 0; i < list.size(); i++) {
            int j = i;
            pool.execute(()->{
                Integer integer = list.get(j);
                log.debug("{}",integer);
            });
        }
    }
}
