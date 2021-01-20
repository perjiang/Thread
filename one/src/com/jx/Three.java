package com.jx;

import java.sql.SQLOutput;

public class Three {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("running");
        }, "t1");
        t1.run();  // 直接调用run方法是主线程来执行  并不是新的线程来执行


        t1.start();//
    }
}
