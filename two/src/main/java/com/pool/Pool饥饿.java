package com.pool;

import com.mysql.jdbc.log.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 固定线程池出现的问题
 */
@Slf4j(topic = "c.pool")
public class Pool饥饿 {
    static final List<String> MENU = Arrays.asList("1","2","3","4","5");
    static Random random = new Random();
    static String cookie(){ return MENU.get(random.nextInt(MENU.size()));}

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);  // 执行点菜任务
        ExecutorService service = Executors.newFixedThreadPool(2);  //执行做菜任务
        pool.execute(()->{
            log.debug("开始点菜");
            Future<String> task1 = service.submit(() -> {
                log.debug("做菜");
                return cookie();
            });
            try {
                log.debug("上菜{}",task1.get());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        pool.execute(()->{
            log.debug("开始点菜");
            Future<String> task1 = service.submit(() -> {
                log.debug("做菜");
                return cookie();
            });
            try {
                log.debug("上菜{}",task1.get());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        pool.execute(()->{
            log.debug("开始点菜");
            Future<String> task1 = service.submit(() -> {
                log.debug("做菜");
                return cookie();
            });
            try {
                log.debug("上菜{}",task1.get());
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
