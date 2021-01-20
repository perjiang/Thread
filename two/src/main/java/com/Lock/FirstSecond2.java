package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 按照先手顺序进行打印
 */

@Slf4j(topic = "c.FirstSecond")
public class FirstSecond2 {
    static  ReentrantLock lock = new ReentrantLock();
    static Condition c1 = lock.newCondition();
    static boolean flag = false;

    public static void main(String[] args) {

//        new Thread(()->{
//            lock.lock();
//            try {
//                while (flag == false){
//                    log.debug("2还么有打印等一会儿");
//                    try {
//                        c1.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                log.debug("1");
//            }finally {
//                lock.unlock();
//            }
//        },"t1").start();
//
//        new Thread(()->{
//            lock.lock();
//            try {
//                TimeUnit.SECONDS.sleep(2);
//                log.debug("2");
//                c1.signalAll();
//                flag = true;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }finally {
//                lock.unlock();
//            }
//        },"t2").start();


        Thread t = new Thread(()->{
            LockSupport.park();
            log.debug("1");
        },"t3");
        t.start();

        new Thread(()->{
            log.debug("2");
            LockSupport.unpark(t);
        },"t3").start();



    }
}
