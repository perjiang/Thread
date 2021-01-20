package com.Lock;

import com.jx.ThreadPark;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尝试获取锁
 */
@Slf4j(topic = "c.TryLock")
public class TryLock {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("尝试获取锁");
            try {
                if (lock.tryLock(3,TimeUnit.SECONDS)) {
                    try {
                        log.debug("获取到了锁");
                        TimeUnit.SECONDS.sleep(2);
                    } finally {
                        log.debug("释放锁");
                        lock.unlock();
                    }
                } else {
                    log.debug("获取锁失败，直接退出");
                    return;
                }
            } catch (InterruptedException e) {
                log.debug("不知道被那个打断了");
                e.printStackTrace();
                return;
            }
        }, "t1");
        log.debug("主线程来加锁");
        lock.lock();
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
