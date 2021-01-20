package com.Sig;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

/**
 * 懒汉单例  直接使用synchronize加锁 每次创建实例都需要加锁获取有点影响性能(使用double check解决)
 */
@Slf4j(topic = "c.Sigton03")
public class Sigton03 {
     static volatile Sigton03 instance = null;
    private Sigton03(){}
    public  synchronized  static Sigton03 getInstance(){
        if (instance==null){
            log.info(Thread.currentThread().getName()+"进来了");
            instance = new Sigton03();
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 10; i++) {
//            new Thread(()->{
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Sigton03 instance = Sigton03.getInstance();
//                log.info(instance.hashCode()+"");
//            }).start();
//        }
//
//        TimeUnit.SECONDS.sleep(1);
//        Sigton03 instance = Sigton03.getInstance();
//        Sigton03 instance1 = Sigton03.getInstance();
//        log.info(instance.hashCode()+"");
//        log.info("单线程获取{}",instance==instance1);

        // 反射破坏单例
        Constructor<?> constructor = Class.forName("com.Sig.Sigton03").getDeclaredConstructor(null);
        constructor.setAccessible(true);
        Sigton03 sigton03 = (Sigton03)constructor.newInstance();
        Sigton03 sigton04 = (Sigton03)constructor.newInstance();
        System.out.println(sigton03==sigton04);
    }
}
