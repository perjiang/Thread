package com.join;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 一个线程等待另一个线程的结果作为参数
 */
@Slf4j(topic = "c.waitok")
public class waitok {

    public static void main(String[] args) {
        // t1等待t2的结果
        zhong3 zhong = new zhong3();
        Thread t1 = new Thread(()->{
            log.debug("等待结果");
            Object o = zhong.get(5000);
            log.debug("最后的结果是{}",o);
        },"t1");
        t1.start();

        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                zhong.interr();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2");
        t2.start();

        Thread t3 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(6);
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

@Slf4j(topic = "c.zhong3")
class zhong3{
    Object response;

    public Object get(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long passtime = 0;
            while (response == null){
                long wait = timeout - passtime;
                if (wait<=0){
                    break;
                }
                try {
                    this.wait(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passtime = System.currentTimeMillis() - begin;  // 经历了多少时间

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
