package com.jx;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadJoin")
public class ThradJoin {

    static  int r = 0;

    public static void main(String[] args) throws Exception {
        method1();
    }

    public static void  method1() throws  Exception{
        log.debug("开始");
        Thread thread = new Thread(() -> {
            log.debug("开始");
            try {
                Thread.sleep(1000);
                log.debug("结束");
                r = 10;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        thread.start();
        thread.join();   // 程序运行到此处  主线程会等待Thread线程运行结束才会继续往下执行
        log.debug("r最后的结果是{}",r);
        log.debug("结束");
    }
}
