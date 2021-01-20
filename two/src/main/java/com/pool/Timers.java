package com.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.swing.plaf.nimbus.State;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度器
 */
@Slf4j(topic = "c.Timers")
public class Timers {
    static int number = 0;
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
       method2(pool);

    }

    private static void method3(ScheduledExecutorService pool) {
        log.debug("begin");
        pool.scheduleWithFixedDelay(()->{
           number++;
            log.debug("{}","第"+number+"次");
        },1,1, TimeUnit.SECONDS);
    }

    private static void method2(ScheduledExecutorService pool) {
        log.debug("begin");
        pool.schedule(()->{
            log.debug("第一个开始执行");
//            try {
                int i = 1 / 0;
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            },1, TimeUnit.SECONDS);
        pool.schedule(()->{log.debug("第e二个开始执行");},4, TimeUnit.SECONDS);
        pool.shutdown();
    }

    private static void mthod1(){
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task2");
            }
        };
        log.debug("start");
        timer.schedule(task1,1000);
        timer.schedule(task2,1000);

    }
}
