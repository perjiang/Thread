package com.Wait;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.smoke")
public class Smoke {

    static boolean hasSmoke = false;
    final static Object LOCK = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
            synchronized (LOCK){
                log.debug("有没有烟{}",hasSmoke);
                if (!hasSmoke){
                    log.debug("没有烟，先歇着");
                    try {
//                        TimeUnit.SECONDS.sleep(5);
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有没有烟{}",hasSmoke);
                if (hasSmoke){
                    log.debug("烟来了，干活");
                }
            }
        },"烟鬼");
        t1.start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (LOCK){
                    log.debug("干活");
                }
            },"其他人").start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
            synchronized (LOCK){
                log.debug("送烟");
                hasSmoke = true;
                LOCK.notify();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
