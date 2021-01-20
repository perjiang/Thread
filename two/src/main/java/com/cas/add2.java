package com.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class add2 {
        static AtomicInteger num = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
           new Thread(()->{
               while (num.get()<1000){
                    num.getAndIncrement();
               }
           }).start();

        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------");
        System.out.println(num.get());
    }
}
