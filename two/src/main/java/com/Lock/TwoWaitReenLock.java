package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock来实现等待
 */
@Slf4j(topic = "c.TwoWaitReenLock")
public class TwoWaitReenLock {
    static  ReentrantLock lock = new ReentrantLock();
    static boolean hasSmoke = false;
    static boolean hasEat = false;
    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();
    public static void main(String[] args) {


        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                log.debug("有没有烟{}", hasSmoke);
                while (!hasSmoke) {
                    try {
                        log.debug("没有烟休息去了");
                        c1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有没有烟{}", hasSmoke);
                if (hasSmoke) {
                    log.debug("烟来了，干活");
                }
            } finally {
                lock.unlock();
            }
        }, "烟鬼");
        thread.start();
        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                log.debug("有没有外卖{}", hasEat);
                while (!hasEat) {
                    try {
                        log.debug("没有外卖息去了");
                        c2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有没有外卖{}", hasEat);
                if (hasEat) {
                    log.debug("外卖来了，干活");
                }
            } finally {
                lock.unlock();
            }
        }, "饿死鬼");
        thread2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        get(name);
    }

    public static void get(String name){
        lock.lock();
        try {
            log.debug("{}来了",name);
            if (name.equals("烟")){
                hasSmoke = true;
                c1.signalAll();
            }if (name.equals("外卖")){
                hasEat = true;
                c2.signalAll();
            }
        }finally {
            lock.unlock();
        }
    }
}
