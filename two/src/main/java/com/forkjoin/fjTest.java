package com.forkjoin;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join使用
 */


public class fjTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(pool.invoke(new Mytask(5)));
    }
}

class Mytask extends RecursiveTask<Integer>{

    private int n;
    public Mytask(int n){
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n==1){
            return 1;
        }
        Mytask t1 = new Mytask(n - 1);  //拆分任务
        t1.fork();      // 新的线程来执行任务
        Integer result = n + t1.join();        // 获取结果
        return result;
    }
}
