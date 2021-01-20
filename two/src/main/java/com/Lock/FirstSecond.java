package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 按照先手顺序进行打印
 */

@Slf4j(topic = "c.FirstSecond")
public class FirstSecond {
    static Object lock = new Object();
    static boolean flag = false;

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock){
                while (!flag){
                    log.debug("还么有打印，先等一会儿");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        },"t1").start();


        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock){
              log.debug("2");
              flag = true;
              lock.notifyAll();
            }
        },"t2").start();



    }
}
