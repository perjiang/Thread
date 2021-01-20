package com.cas;

import com.sun.tools.javac.Main;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * aba问题的解决
 * 加版本号
 */

@Slf4j(topic = "c.ForABA")
public class ForABA {
    static AtomicStampedReference<String> str = new AtomicStampedReference<>("A", 1);

    public static void main(String[] args) throws InterruptedException {
        log.debug("start");
        // 1.获取str当前的值
        String old = str.getReference();
        // 2.获取str当前版本号 1
        int stamp = str.getStamp();
        other();
        TimeUnit.SECONDS.sleep(2);
        log.debug("{}",stamp);
        //9. 当前str的值是A 版本号是 3 而主线程拿来比较的值是A但是版本号仍然是1 比对失败，没有完成A--->C的过程
        log.debug("chang A--->C:{}",str.compareAndSet(old,"C", stamp,stamp+1));
        log.debug("end:{}",str.getReference());
    }

    public static void other(){
        new Thread(()->{
            // 3.获取当前值 A
            String old = str.getReference();
            // 4.获取当前版本号 1
            int stamp = str.getStamp();
            log.debug("{}",stamp);
            // 5.执行比对 现在str的值是A 版本号是 1 比对成功  str变为B stamp = 2；
            log.debug("changeA--->B:{}",str.compareAndSet(old,"B",stamp,stamp+1));
        },"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            // 6.获取当前值 B
            String old = str.getReference();
            // 7.获取当前版本号 2
            int stamp = str.getStamp();
            log.debug("{}",stamp);
            // 8.执行比对 现在str的值是B 版本号是 2 比对成功  str变为A stamp = 3；
            log.debug("changeB--->A:{}",str.compareAndSet(old,"A",stamp,stamp+1));
        },"t2").start();
    }
}
