package com.Syn;

import jdk.jfr.StackTrace;
import lombok.extern.slf4j.Slf4j;

/**
 * 非线程安全的计算
 */
@Slf4j(topic = "c.Count")
public class Count {

     volatile static int i =0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (Count.class){
                for (int j = 0; j < 5000; j++) {
                    i++;
                }
            }

        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (Count.class){
                for (int n = 0; n < 5000; n++) {
                    i--;
                }
            }

        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("i最后的结果是{}",i);
    }

}
