package com.state;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 线程runnable和watting之间的转换
 */

@Slf4j(topic = "c.runableTowaitting")
public class runableTowaitting {

    static final Object LOCK = new Object();
    public static void main(String[] args) {

       new Thread(()->{
            synchronized (LOCK){
                log.debug("t1开始执行");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t1等待结束");
            }
        },"t1").start();

       new Thread(()->{
            synchronized (LOCK){
                log.debug("t2开始执行");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2等待结束");
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
            log.debug("开始去唤醒他们");
            synchronized (LOCK){
                LOCK.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

}
