package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试 ReentrantLock的可重入性
 */

@Slf4j(topic = "c.TestReentrant")
public class TestReentrant {
    public static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

            lock.lock();
            try {
                log.debug("main执行");
                method1();
            }finally {
                lock.unlock();
            }

    }

    public static void method1(){
        lock.lock();
        try {
            log.debug("method1执行");
            method2();
        }finally {
            lock.unlock();
        }
    }

    public static void method2(){
        lock.lock();
        try {
            log.debug("method2执行");
        }finally {
            lock.unlock();
        }
    }
}
