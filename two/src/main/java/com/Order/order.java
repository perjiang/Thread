package com.Order;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.order")
public class order {

    static int x ;
    public static void main(String[] args) {

        Thread thread = new Thread(()->{
            x = 5;
        });
        thread.start();

        log.debug("{}",x);
    }

}
