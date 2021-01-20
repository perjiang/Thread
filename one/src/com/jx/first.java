package com.jx;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.zip.DeflaterOutputStream;

public class first {

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(()->{
            System.out.println("123523"+Thread.currentThread().getName());
        },"one");
        t1.start();

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("222222");
            }
        };

        //lamda表达式的使用
        Runnable r2 = ()->{
            System.out.println("222");
        };


        Thread t2 = new Thread(r1,"two");
    }

}
