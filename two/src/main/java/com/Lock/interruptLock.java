package com.Lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.interruptLock")
public class interruptLock {

    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            try {
                log.debug("尝试获取锁");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            try {
                log.debug("获取到锁");
            }finally {
                lock.unlock();
            }
        });
        lock.lock();
        thread.start();
        log.debug("去打断");
        thread.interrupt();
    }

}
