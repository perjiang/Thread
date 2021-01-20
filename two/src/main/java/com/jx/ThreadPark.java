package com.jx;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 *
 */
@Slf4j(topic = "c.ThreadPark")
public class ThreadPark {
    public static void main(String[] args) throws InterruptedException {
        Park();
    }

    public static void Park() throws InterruptedException{
        Thread t1 = new Thread(()->{
            log.debug("park开始");
            LockSupport.park();
            log.debug("park被打断了");
            log.debug("线程当前的状态{}",Thread.currentThread().isInterrupted());
        },"t1");

        t1.start();
        TimeUnit.SECONDS.sleep(2);// 修面2s打断park状态
        t1.interrupt();

    }

}
