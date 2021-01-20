package com.Lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.concurrent.TimeUnit;

/**
 * 活锁：在互相改变对方的结束条件 导致双方都不能结束
 */
@Slf4j(topic = "c.LiveLock")
public class LiveLock {
    public static final Object lock = new Object();
    static volatile int count = 10;

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (lock){
                while (count>0) {
                    count--;
                    log.debug("count{}",count);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        },"t1").start();

        new Thread(()->{
            synchronized (lock){
                while (count<20){
                    count++;
                    log.debug("count{}",count);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        },"t2").start();
    }
}
