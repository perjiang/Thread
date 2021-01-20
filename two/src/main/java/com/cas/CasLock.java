package com.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用cas实现锁原理
 */

@Slf4j(topic = "c.CasLock")
public class CasLock {
    AtomicInteger integer = new AtomicInteger(0);
    // 0 无锁
    // 1 有锁
    public void lock(){
        while (true){
            if (integer.compareAndSet(0,1)){
                break;
            }
        }
    }

    public void unlock(){
        log.debug("unlock");
        integer.set(0);
    }


    public static void main(String[] args) {
        CasLock lock = new CasLock();
        new Thread(()->{
            log.debug("begin");
            lock.lock();
            try {
                log.debug("lock");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            log.debug("begin");
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }
}
