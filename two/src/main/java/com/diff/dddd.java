package com.diff;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.ddd")
public class dddd {

    public static   Object lock = new Object();
    public static ReentrantLock l = new ReentrantLock();
    public static Condition condition = l.newCondition();
    public static void main(String[] args) {

//        new Thread(()->{
//            long start = System.currentTimeMillis();  // 开始时间
//            long use = 0; // 已经使用的时间
//            while (true){
//                synchronized (lock){
//                    if (use>1000){
//                        log.debug("break");
//                        break;
//                    }
//                    try {
//                        log.debug("start");
//                        lock.wait(300);
//                        log.debug("end");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    use = System.currentTimeMillis() - start;  // 已经使用的时间
//
//                }
//            }
//        }).start();

        new Thread(()->{
            offer(10,TimeUnit.NANOSECONDS);
        }).start();
    }



    public static void offer(long timeout, TimeUnit timeUnit){
        l.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (true) {
                try {
                    if(nanos <= 0) {
                        break;
                    }
                    nanos = condition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            l.unlock();
        }
    }
}
