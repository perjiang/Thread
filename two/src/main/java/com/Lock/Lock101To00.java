package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 演示偏向锁升级到轻量级锁
 */

@Slf4j(topic = "c.Lock101To00")
public class Lock101To00 {
    static final Object OBJECT = new Object();
    public static void main(String[] args) {
        Lock lock = new Lock();
        new Thread(()->{
            synchronized (lock){  // 偏向锁
                log.debug("偏向锁");
            }
            synchronized (OBJECT){
                OBJECT.notify();
            }
        },"t1").start();
        new Thread(()->{
            synchronized (OBJECT){   // t2线程在此处陷入等待，要等t1执行唤醒才能继续往下执行 这就保证了lock没有产生竞争
                try {
                    OBJECT.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lock){    // 再次加锁升级为轻量级锁
                log.debug("重量级锁");
            }
        },"t2").start();
    }
}
class  Lock{}