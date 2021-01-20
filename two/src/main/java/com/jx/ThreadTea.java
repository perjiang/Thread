package com.jx;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 统筹法实现喝茶
 */

@Slf4j(topic = "c.ThreadTea")
public class ThreadTea {


    // 第一个方法
    public static void method1() throws InterruptedException {

        Thread t1 = new Thread(()->{
            log.debug("洗好水壶");
            log.debug("灌水");
            log.debug("烧水");
            try {
                TimeUnit.SECONDS.sleep(10);  // 这三个总共花费时间10s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("水开了");

        },"t1");

        Thread t2 = new Thread(()->{
            // 在烧水期间创建线程做其他事情
            log.debug("洗茶壶");
            log.debug("洗茶杯");
            log.debug("拿茶叶");
            try {
                TimeUnit.SECONDS.sleep(5);  //这三个动作花费5s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("东西洗好了");
            try {
                t1.join();
                log.debug("泡茶");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2");

        t1.start();
        t2.start();
    }


    public static void  method2(){
        Thread t1 = new Thread(()->{
            log.debug("洗好水壶");
            log.debug("灌水");
            log.debug("烧水");
            try {

                TimeUnit.SECONDS.sleep(10);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"a");

        Thread t2 = new Thread(()->{
            // 在烧水期间创建线程做其他事情
            log.debug("洗茶壶");
            log.debug("洗茶杯");
            log.debug("拿茶叶");
            try {
                TimeUnit.SECONDS.sleep(5);  //这三个动作花费5s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("东西洗好了");
        },"t2");
        t1.start();
        t2.start();
    }


    public static void main(String[] args) throws InterruptedException {
        method1();


    }
}
