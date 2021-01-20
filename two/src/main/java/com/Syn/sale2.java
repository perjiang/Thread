package com.Syn;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.VarHandle;

@Slf4j(topic = "c.sale2")
public class sale2 extends Thread {
    static  int  number = 20;
    static boolean flag = true;

    public static void sale() throws InterruptedException {

            while (flag){
                synchronized (sale2.class){
                    if (number<=0){
                        flag = false;
                        break;
                    }
                number=number-1;
                log.debug("当前线程{},还剩{}",Thread.currentThread().getName(),number);
                Thread.sleep(100);
            }
        }
    }
    @SneakyThrows
    @Override
    public void run() {
            sale();
    }
    public static void main(String[] args) {
        sale2 sale2 = new sale2();
        Thread t1 = new Thread(sale2,"t1");
        Thread t2 = new Thread(sale2,"t2");
        t1.start();
        t2.start();

    }
}
