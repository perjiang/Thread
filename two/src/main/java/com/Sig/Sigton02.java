package com.Sig;

import lombok.extern.slf4j.Slf4j;

/**
 * 饿汉单例
 */
@Slf4j(topic = "c.Sigton02")
public class Sigton02 {
    final static Sigton02 instance = new Sigton02();
    private Sigton02(){}
    public static Sigton02 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Sigton02 instance = Sigton02.getInstance();
                log.info(instance.hashCode()+"");
            }).start();
        }
    }
}
