package com.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.blaking")
public class blaking {
    private volatile  boolean flag = false;

    public void start(){
        log.debug("开始执行");
        synchronized (this){
            if (flag){
                log.debug("我不在执行了");
                return;
            }
            log.debug("执行了");
            flag = true;
        }
    }

    public static void main(String[] args) {
        blaking blaking = new blaking();
        Thread thread = new Thread(()->{
            blaking.start();
        },"t1");

        Thread thread2 = new Thread(()->{
            blaking.start();
        },"t2");

        thread.start();
        thread2.start();

    }

}
