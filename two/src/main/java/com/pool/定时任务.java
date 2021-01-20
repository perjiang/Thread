package com.pool;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务需求：每周的周四 18.00执行某段代码
 */
@Slf4j(topic = "c.定时任务")
public class 定时任务 {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now(); // 获取当前时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);  // 获取本周四的18.00
        System.out.println(now);
        System.out.println(time);
        // 如果当前的时间已经过了本周四的18.00 则要推迟到下一周
        if (now.compareTo(time)>0){
            time = time.plusWeeks(1);
        }
        System.out.println(time);
        long millis = Duration.between(now, time).toMillis();  //两个时间相差的毫秒值
        System.out.println(millis);
        long jiange = 1000 * 60 * 60 * 24 * 7;

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleWithFixedDelay(()->{
            log.debug("执行任务啦");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("执行完毕了");
        }, millis,jiange,TimeUnit.MILLISECONDS);
    }
}
