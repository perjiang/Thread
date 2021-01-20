package com.Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 顺序打印
 */

@Slf4j(topic = "c.ss")
public class 顺序打印 {
    static Object lock = new Object();
    static int number = 1;

    public static void main(String[] args) {


//        new Thread(()->{
//            for (int i = 0; i < 3; i++) {
//                synchronized (lock){
//                    while (number!=1){
//                        try {
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    log.debug("a");
//                    number = 2;
//                    lock.notifyAll();
//                }
//            }
//
//        },"t1").start();

//        new Thread(()->{
//            for (int i = 0; i < 3; i++) {
//                synchronized (lock){
//                    while (number!=2){
//                        try {
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    log.debug("b");
//                    number = 3;
//                    lock.notifyAll();
//                }
//            }
//
//        },"t2").start();

//        new Thread(()->{
//            for (int i = 0; i < 3; i++) {
//                synchronized (lock){
//                    while (number!=3){
//                        try {
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    log.debug("c");
//                    number = 1;
//                    lock.notifyAll();
//                }
//            }
//
//        },"t3").start();
    WaitOrNotify waitOrNotify = new WaitOrNotify(1,5);
    new Thread(()->{
        waitOrNotify.print("a",1,2);
    },"t1").start();

        new Thread(()->{
            waitOrNotify.print("b",2,3);
        },"t2").start();

        new Thread(()->{
            waitOrNotify.print("c",3,1);
        },"t3").start();
    }
}


class WaitOrNotify{
    private int flag;
    private int count;
    public WaitOrNotify(int flag,int count){
        this.count = count;
        this.flag = flag;
    }

    public  void print(String str,int waitFlag,int nextFlag){
        for (int i = 0; i < count; i++) {
            synchronized (this){
                while (flag!=waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }

        }
    }

}
