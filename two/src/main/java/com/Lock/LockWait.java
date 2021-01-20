package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.LockWait")
public class LockWait {
    static final Object lock = new Object();  //同一个锁对象
    public static void main(String[] args) throws InterruptedException {
       Thread t0= new Thread(()->{
           synchronized (lock){
                   log.debug("开始休息");
                   try {
                       lock.wait();  //1.在此处t0释放掉cpu资源
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

           }
           log.debug("休息结束"); //4.被唤醒继续执行
       },"t0");

        Thread t1 = new Thread(()->{
            try {    // 为了避免线程t1先获得执行权执行唤醒操作（这个时候还没有wait的线程，就是无意义的唤醒）
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock){  //2.t1获取到了同一把锁，
                log.debug("开始唤醒");
                lock.notify();   //3. 唤醒了在休眠状态已经获取过lock锁的线程t0
            }
        },"t1");
        t1.start();
        t0.start();
    }
}
