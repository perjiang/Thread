package com.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j(topic = "c.booleans")
public class booleans {
    public static void main(String[] args) throws InterruptedException {
        safeBooleans safeBooleans = new safeBooleans();
        Thread t1 = new Thread(()->{
            safeBooleans.change();
        });
        Thread t2 = new Thread(()->{
            safeBooleans.change();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}",safeBooleans.getAtomicBoolean());

    }

}
class safeBooleans{
    private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public Boolean getAtomicBoolean() {
        return atomicBoolean.get();
    }

    public  void change(){
        while (true){
            boolean old = atomicBoolean.get();
            boolean news = !old;
            if (atomicBoolean.compareAndSet(old,news)){
                break;
            }
        }
    }
}
