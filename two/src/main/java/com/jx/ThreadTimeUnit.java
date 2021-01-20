package com.jx;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.ThreadTimeUnit")
public class ThreadTimeUnit {


    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                log.debug("start");
                TimeUnit.SECONDS.sleep(2);
                log.debug("end");
            } catch (InterruptedException e) {
                log.debug("exception");
            }
        });

        thread.start();

    }
}
