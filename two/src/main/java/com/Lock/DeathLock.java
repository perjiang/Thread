package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 */

@Slf4j(topic = "c.DeathLock")
public class DeathLock {
    final static Object A = new Object();
    final static Object B = new Object();
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (A){
                log.debug("获取到A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (B){
                        log.debug("获取到B锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            synchronized (B){
                log.debug("获取到B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (A){
                        log.debug("获取到A锁");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();


    }
}
