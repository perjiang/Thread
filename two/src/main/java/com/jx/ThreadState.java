package com.jx;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadState")
public class ThreadState {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("running");

        }, "t1");

        log.debug(String.valueOf(t1.getState()));
        t1.run();
        log.debug(String.valueOf(t1.getState()));

    }
}
