package com.Lock;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j(topic = "c.ReentrantLockForSale")
public class ReentrantLockForSale {
    static Random random = new Random();

    public static int randomAmont(){
        return random.nextInt(5)+1;
    }
    public static void main(String[] args) throws InterruptedException {
        Sale sale = new Sale(2000);
        List<Integer> amountList = new Vector<>();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(()->{
                int sell = sale.sales(randomAmont());
                amountList.add(sell);
                try {
                    Thread.sleep(randomAmont());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            threadList.add(thread);
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        log.debug("余票:{}",sale.getNumber());
        log.debug("卖出去的票:{}",amountList.stream().mapToInt(i->i).sum());

    }
}


@Slf4j(topic = "c.Sale")
class Sale extends ReentrantLock {
    private int number;
    private boolean flag = true;
    public Sale(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int sales(int num) {
        lock();
        try {
            if (this.number>=num){
                this.number-=num;
                return num;
            }else {
                return 0;
            }
        }finally {
            unlock();
        }

    }
}
