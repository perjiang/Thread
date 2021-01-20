package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


@Slf4j(topic = "c.TwoLock")
public class TwoLock {

    public static void main(String[] args) {
        BigRom rom = new BigRom();


        new Thread(()->{
            rom.study();
        },"t1").start();

        new Thread(()->{
            rom.Sleep();
        },"t2").start();


    }


}


@Slf4j(topic = "c.BigRom")
class BigRom{
    final Object sleepRoom = new Object();
    final Object studyRoom = new Object();
    public void study(){
        synchronized (studyRoom){
            log.debug("进来学习");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void Sleep(){
        synchronized (sleepRoom){
            log.debug("进来睡觉");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
