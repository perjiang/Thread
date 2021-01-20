package com.park;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.Test")
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("begin sleep");
            try {
                TimeUnit.SECONDS.sleep(5);
                log.debug("开始park");
                LockSupport.park();
                log.debug("结束park");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("准备唤醒t1");
        LockSupport.unpark(t1);

    }
}
