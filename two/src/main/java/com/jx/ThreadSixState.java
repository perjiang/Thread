package com.jx;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程6中状态
 */

@Slf4j(topic = "c.ThreadSixState")
public class ThreadSixState {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{

        },"t1");


        Thread t2 = new Thread(()->{
            while (true){

            }
        },"t2");
        t2.start();


        Thread t3 = new Thread(()->{
            log.debug("running");
        },"t3");
        t3.start();

        Thread t4 = new Thread(()->{
            synchronized (ThreadSixState.class){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t4");
        t4.start();

        Thread t5 = new Thread(()->{
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t5");
        t5.start();


        Thread t6 = new Thread(()->{
            synchronized (ThreadSixState.class){
                try {
                    Thread.sleep(4000);
                    log.debug("获取到了锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t6");
        t6.start();

        Thread.sleep(500);


        log.debug("t1状态{}",t1.getState());
        log.debug("t2状态{}",t2.getState());
        log.debug("t3状态{}",t3.getState());
        log.debug("t4状态{}",t4.getState());
        log.debug("t5状态{}",t5.getState());
        log.debug("t6状态{}",t6.getState());

    }
}
