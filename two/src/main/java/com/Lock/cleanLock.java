package com.Lock;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.cleanLock")
public class cleanLock {
        static int x = 0;
    public static void main(String[] args) {
        method1();
        method2();
    }

    public static void method1(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            x++;
        }

        long end = System.currentTimeMillis();
        log.debug("使用的是时间是{}",end-start);
    }

    public static void method2(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Object object = new Object();
            synchronized (object){
                x++;
            }
        }


        long end = System.currentTimeMillis();
        log.debug("使用的是时间是{}",end-start);
    }

}
