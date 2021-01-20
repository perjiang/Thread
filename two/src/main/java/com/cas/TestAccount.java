package com.cas;

import org.springframework.aop.support.AopUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestAccount {
    public static void main(String[] args) {
//        Account account = new UnsalfAccount(10000);
//        Account.demo(account);
        Account safe = new SafeAccount(10000);
        Account.demo(safe);
    }
}




class UnsalfAccount implements Account{
    private Integer balance;

    public UnsalfAccount(Integer balance){
        this.balance = balance;

    }

    @Override
    public synchronized Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        this.balance-=amount;
    }
}


class SafeAccount implements Account{
    private AtomicInteger blance;

    public SafeAccount(int blance){
        this.blance = new AtomicInteger(blance);
    }

    @Override
    public Integer getBalance() {
        return blance.get();
    }

    @Override
    public void withdraw(Integer amount) {
//        while (true){
//            int old = blance.get();  // 获取最新值
//            int news = old - amount;  // 发生了改变应该变成的值
//            if (blance.compareAndSet(old,news)){        // 进行比对  成功就退出
//                break;
//            }
//        }
        blance.getAndAdd(-amount);
    }
}

interface Account{
    Integer getBalance();
    // 取款
    void withdraw(Integer amount);

    static void demo(Account account){
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(()->{
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread->{
            Thread.start();
        });
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");

    }
}
