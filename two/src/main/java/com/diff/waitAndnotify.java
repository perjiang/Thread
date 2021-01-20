package com.diff;

import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * 验证sleep不会释放锁
 * wait会释放锁
 */


@Slf4j(topic = "c.waitAndnotify")
public class waitAndnotify {

     final static Object lock1 = new Object();
     final static Object lock2 = new Object();
        static boolean b;
    public static void main(String[] args) {
        System.out.println(!false);
        new Thread(()->{
            synchronized (lock1){
                log.debug("sleepT1获得锁");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1){
                log.debug("sleepT2获得锁");
            }
        },"t2").start();



        new Thread(()->{
            synchronized (lock2){
                log.debug("waitT3获得锁");
                try {
                    lock2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t3").start();


        new Thread(()->{
            synchronized (lock2){
                log.debug("waitT4获得锁");
                try {
                    TimeUnit.SECONDS.sleep(2);
                    lock2.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"t4").start();


    log.debug("aaa");
    }
}
