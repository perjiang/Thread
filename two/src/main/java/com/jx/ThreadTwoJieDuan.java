package com.jx;

import lombok.extern.slf4j.Slf4j;

/**
 * 当程序被打断会出现的情况:
 *      1.当程序在正常运行时被打断 这个时候flag=true
 *      2.当程序在休眠是被打断，会直接抛出异常但是flag=false
 *      3.如何在休眠时设置打断标记呢？：在catch代码块中再次打断当前线程，catch也是属于正常运行，这个时候flag=true
 *      4.在catch快中手动设置flag=true有用吗？
 *          无用，flag只会在catch中为true，当出了catch循环获取flag状态时，发现线程是休眠是被打断，休眠时打断flag=false
 */
@Slf4j(topic = "c.ThreadTwoJieDuan")
public class ThreadTwoJieDuan {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true){
                log.debug("当前线程名称:{}",Thread.currentThread().getName());
                boolean flag = Thread.currentThread().isInterrupted();
                log.debug("到现在flag的值是{}",flag);
                if (flag==true){
                    log.debug("我被打断了，我要退出了");
                    break;
                }
                    try {
                        log.debug("我正在执行监控任务,准备手动设置flag");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // 在异常里重置打断状态（默认在睡眠被打断flag=false，再次打断强制flag=true）
                        flag = Thread.currentThread().isInterrupted();
                        log.debug("{}",flag);
                        e.printStackTrace();

                    }


            }
        }, "t1");

        t1.start();
        int r = 0;
        while (r!=10){
            r++;
            if (r==10){
                log.debug("监控了很久了，我要打断t1");
                t1.interrupt();
            }
            log.debug("现在r的值是:{}",r);

        }
    }

}
