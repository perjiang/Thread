package com.jx;

import jdk.jfr.SettingControl;

public class demo2 {
    ThreadLocal<String> local = new ThreadLocal<>();
    private String content;

    public String getContent() {
        return local.get();
    }

    public void setContent(String content) {
        local.set(content);
    }


    public static void main(String[] args) {
        demo2 demo2 = new demo2();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    demo2.setContent(Thread.currentThread().getName()+"的数据");
                    System.out.println("-------------------");
                    System.out.println(Thread.currentThread().getName()+"------->"+demo2.getContent());
                }
            });
            thread.setName("线程"+i);
            thread.start();
        }
    }
}
