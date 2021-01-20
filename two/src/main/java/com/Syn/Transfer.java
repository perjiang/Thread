package com.Syn;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 转账
 */
@Slf4j(topic = "c.Transfer")
public class Transfer {
    public static void main(String[] args) throws InterruptedException {
        Bank a = new Bank(1000);
        Bank b = new Bank(1000);


        Thread t1 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                a.gets(b,getRandom());
            }

        },"t1");

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 100; i++) {
                b.gets(a,getRandom());
            }

        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("总共的钱{}",a.getMoney()+b.getMoney());

    }


    static Random random = new Random();
    public static int getRandom(){
        return random.nextInt(100)+1;
    }


}


class Bank{
    private int money;
    public Bank(int money){
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void gets(Bank target, int count){
        synchronized (Bank.class) {        // 测试代码中有两个Bank对象，这个时候方法上加锁已经没用，方法加锁是针对同一个对象  这里直接给class加锁
            if (getMoney() >= count) {
                target.setMoney(target.getMoney() + count);
                this.setMoney(this.getMoney() - count);
            }
        }
    }
}