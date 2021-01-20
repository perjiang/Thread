package com.Wait;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.two")
public class two {
    public static void main(String[] args) {

        new Thread(()->{
            log.debug("begin");
        },"t1").start();

        new Thread(()->{
            log.debug("begin");
        },"t2").start();



    }


}
