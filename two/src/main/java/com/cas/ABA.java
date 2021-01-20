package com.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * aba问题在现
 */
@Slf4j(topic = "c.ABA")
public class ABA {
    static AtomicReference<String> str = new AtomicReference<>("A");

    public static void main(String[] args) {
        log.debug("start");
        String old = str.get();
        other();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("change A-->C,{}",str.compareAndSet(old,"C"));
        log.debug("{}",str.get());
    }
    public  static  void other(){
        Thread t1 = new Thread(()->{
            String old = str.get();
           log.debug("change A--->B,{}", str.compareAndSet(old,"B"));
        },"t1");
            t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(()->{
            String old = str.get();
            log.debug("change B--->A,{}", str.compareAndSet(old,"A"));
        },"t2");
        t2.start();
    }

}
