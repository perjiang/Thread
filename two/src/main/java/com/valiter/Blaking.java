package com.valiter;

import lombok.extern.slf4j.Slf4j;

/**
 * Blaking模式
 */
@Slf4j(topic = "c.Blaking")
public class Blaking {
    public static void main(String[] args) {
        Blak blak = new Blak();
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                blak.start();
            },String.valueOf(i)).start();
        }
    }
}


@Slf4j(topic = "c.Blak")
class Blak{
    private volatile boolean doit = false;

    public void start(){
        synchronized (this){
            if (doit){
                return;
            }
            doit = true;
        }
        new Thread(()->{
            log.debug("start");
        }).start();
    }
}