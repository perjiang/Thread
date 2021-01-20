package com.jx;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.ThreadInterupted")
public class ThreadInterupted {

    public static void main(String[] args) throws  InterruptedException{
        Thread thread = new Thread(() -> {
            while (true){
            boolean interrupted = Thread.currentThread().isInterrupted();
            if (interrupted){
                log.debug("被打断了，退出循环");
                break;
            }
        }
        },"t1");
        thread.start();

        Thread.sleep(1000);
        log.debug("准备开始打断");
        thread.interrupt();
        log.debug("退出了");
    }
}
