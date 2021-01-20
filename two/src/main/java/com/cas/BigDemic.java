package com.cas;

import com.sun.source.tree.WhileLoopTree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BigDemic {
    public static void main(String[] args) {
        DecimalAccount acount = new BigAcount(new BigDecimal("10000"));
        DecimalAccount.demo(acount);
    }

}

class BigAcount implements DecimalAccount{

    //使用AtomicReference传入泛型把该泛型的数据变为cas类型
    private AtomicReference<BigDecimal> balance;

    public BigAcount(BigDecimal balance){
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true){
            BigDecimal old = balance.get();
            BigDecimal news = old.subtract(amount);
            if (balance.compareAndSet(old,news)){
                break;
            }
        }
    }
}

 interface DecimalAccount {
    // 获取余额
    BigDecimal getBalance();
    // 取款
    void withdraw(BigDecimal amount);
    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}