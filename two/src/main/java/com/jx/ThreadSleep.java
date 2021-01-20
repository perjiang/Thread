package com.jx;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadSleep")
public class ThreadSleep {
    public static void main(String[] args) {
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.setPriority(10);
        t2.start();
        log.debug("t2 state {}",t2.getState());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("t2 state {}",t2.getState());
    }
}
