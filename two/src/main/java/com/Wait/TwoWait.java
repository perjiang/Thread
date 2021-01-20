package com.Wait;

import com.mysql.jdbc.log.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TwoWait")
public class TwoWait {
    final static Object LOCK = new Object();
    static boolean hasSmoke = false;
    static boolean hasEat = false;
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (LOCK) {
                log.debug("现在有没有烟,{}", hasSmoke);
                while (!hasSmoke) {
                    try {
                        log.debug("没烟干不了活，歇着");
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("现在有没有烟,{}", hasSmoke);
//                if (hasSmoke){
                    log.debug("烟来了，干活");
//                }else {
//                    log.debug("还是没烟继续休息");
//                }
            }
        },"烟鬼").start();

        new Thread(()->{
            synchronized (LOCK) {
                log.debug("现在有没有外卖,{}", hasEat);
                while (!hasEat) {
                    try {
                        log.debug("没吃饭干不了活，歇着");
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("现在有没有外卖,{}", hasEat);
//                if (hasEat){
                    log.debug("外卖来了，干活");
//                }else {
//                    log.debug("还是没外卖继续休息");
//                }
            }
        },"饿死鬼").start();

        try {
            TimeUnit.SECONDS.sleep(1);
            synchronized (LOCK){
                log.debug("外卖来了");
                hasEat = true;
                LOCK.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
