package com.jx;

import javax.print.DocFlavor;
import java.lang.annotation.Inherited;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class two {

    static Integer sum = 0;
    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();
//        FutureTask<Integer> task = new FutureTask<>(()->{
//            Thread.sleep(1000);
//            return new Integer(22);
//        });
//        Thread t1 = new Thread(task);
//        t1.start();
//        Thread.sleep(1000);
//        Integer integer = task.get();
//
//
//        System.out.println(integer);
//        long end = System.currentTimeMillis();
//        System.out.println(end-start);
        System.out.println(js1());;

    }


    public static Integer js1() throws Exception{
         //Integer sum = 0;
        FutureTask<Integer> task1 = new FutureTask<>(()->{
            Integer a = 0;
            for (int i = 0; i <= 50; i++) {
                a += i;
            }
            return a;
        });
        Thread t1 = new Thread(task1);
        t1.start();
        FutureTask<Integer> task2 = new FutureTask<>(()->{
            Integer b = 0;
            for (int i = 51; i <= 100; i++) {
                b += i;
            }
            TimeUnit.SECONDS.sleep(5);
            return b;
        });
        Thread t2 = new Thread(task2);
        t2.start();
        sum = task1.get()+task2.get();
        return sum;
    }
}
