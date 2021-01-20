package com.Lock;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家问题
 */


public class Eat {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        zxj z1 = new zxj("哲学家1", c1, c2);
        zxj z2 = new zxj("哲学家2", c2, c3);
        zxj z3 = new zxj("哲学家3", c3, c4);
        zxj z4 = new zxj("哲学家4", c4, c5);
        zxj z5 = new zxj("哲学家5", c5, c1);
        z1.start();
        z2.start();
        z3.start();
        z4.start();
        z5.start();

    }

}


@Slf4j(topic = "c.zxj")
class zxj extends Thread{
    Chopstick left;
    Chopstick right;
    public zxj(String name,Chopstick left,Chopstick right){
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
            // 先尝试获取左边筷子
            if (left.tryLock()) {
                try {
                    // 在尝试获取右手筷子
                    if (right.tryLock()) {
                       try {  // 右手也获取成功就执行吃放方法
                           eat();
                       }finally {
                           right.unlock();
                       }
                    }
                }finally {
                    left.unlock();
                }
            }
        }
    }

    public void eat() {
        log.debug("吃饭");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Chopstick extends ReentrantLock {
    String name;
    public Chopstick(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子的名字是"+name;
    }
}
