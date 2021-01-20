package com.join;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 一个线程等待另一个线程的结果作为参数
 */
@Slf4j(topic = "c.waiterr")
public class waiterr {

    public static void main(String[] args) {
        // t1等待t2的结果
        zhong2 zhong = new zhong2();
        Thread t1 = new Thread(()->{
            log.debug("等待结果");
            Object o = zhong.get();
//            int res = (int)o;
            log.debug("最后的结果是{}",o);
        },"t1");
        t1.start();

//        Thread t2 = new Thread(()->{
//            try {
//                TimeUnit.SECONDS.sleep(2);
//                zhong.interr();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"t2");
//        t2.start();

        Thread t3 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum += i;
            }
            log.debug("t3设置值");
            zhong.set(sum);
        },"t3");
        t3.start();
    }
}

@Slf4j(topic = "c.zhong2")
class zhong2{
    Object response;

    public Object get(){
        synchronized (this){
            log.debug("0000");
            while (response == null){
                log.debug("11");
                try {
                    this.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("break");
                break;
            }
            return response;
        }
    }

    public void set(Object response){
        synchronized (this){
                this.response = response;
                this.notifyAll();
        }
    }

    public void interr(){
        synchronized (this){
            log.debug("虚假唤醒");
            this.notifyAll();
        }
    }
}
