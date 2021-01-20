package com.Sig;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 *
 * 双重检查实现单例模式
 */
public class Saigon01 {
    static volatile Saigon01 Saigon01;
    private Saigon01(){};
    private static Saigon01 instance(){
        if(Saigon01==null) {
            synchronized (Saigon01.class) {
                System.out.println(Thread.currentThread().getName()+"获取到了锁");
                if (Saigon01 == null) {
                    Saigon01 = new Saigon01();
                }
            }
        }
         return Saigon01;
    }

    public static void main(String[] args) throws InterruptedException {
        Hashtable hashtable = new Hashtable();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Saigon01 instance = Saigon01.instance();
                hashtable.put(Thread.currentThread().getName(), instance);
            }).start();
        }
        Thread.sleep(2000);
        Saigon01.instance();

        Set set = hashtable.keySet();
        List list = new ArrayList();
        for (Object o : set) {
            Object o1 = hashtable.get(o);
            list.add(o1);
        }

        list.forEach(x-> System.out.println(x.hashCode()));

    }
}
