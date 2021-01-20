package com.Lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.2")
public class 顺序打印2 {

    public static void main(String[] args) {
        Singal singal = new Singal(5);
        Condition a = singal.newCondition();
        Condition b = singal.newCondition();
        Condition c = singal.newCondition();

        new Thread(()->{
            singal.print("A", a, b);
        }).start();
        new Thread(()->{
            singal.print("B", b, c);
        }).start();
        new Thread(()->{
            singal.print("C", c, a);
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
            singal.lock();
            a.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            singal.unlock();
        }

    }

}


@Slf4j(topic = "c.Singal")
class Singal extends ReentrantLock {
    int number;
    public Singal(int number){
        this.number = number;
    }

    // 当所有得线程进来都先等待，我们后面使用一个不相干的线程来唤醒
    public void print(String str, Condition current,Condition next){
        for (int i = 0; i < number; i++) {
            lock();
            try {
                current.await();
                log.debug("{}",str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}