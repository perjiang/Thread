package com.jx;

import java.io.PipedReader;

public class demo1 {
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


    public static void main(String[] args) {
        demo1 demo = new demo1();

        for (int i = 0; i < 5; i++) {
           Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
//                   demo.setContent(Thread.currentThread().getName()+"的数据");
//                   System.out.println("-------------------");
//                   System.out.println(Thread.currentThread().getName()+"------->"+demo.getContent());
                   demo.test();
               }
           });
           thread.setName("线程"+i);
           thread.start();

        }
    }

    public synchronized  void test(){
        this.setContent(Thread.currentThread().getName()+"的数据");
        System.out.println("-------------------");
        System.out.println(Thread.currentThread().getName()+"------->"+this.getContent());
    }
}
