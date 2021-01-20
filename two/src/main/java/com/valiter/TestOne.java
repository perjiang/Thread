package com.valiter;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 可见性问题
 */
@Slf4j(topic = "c.TestOne")
public class TestOne {
    static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while (run){
                log.debug("run");
            }
        });
        t.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("end");
        run = false;
    }
}
