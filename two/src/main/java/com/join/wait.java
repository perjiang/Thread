package com.join;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.sun.java.accessibility.util.TopLevelWindowListener;
import lombok.extern.slf4j.Slf4j;

import javax.lang.model.type.TypeKind;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程等待另一个线程的结果作为参数
 */
@Slf4j(topic = "c.wait")
public class wait {

    public static void main(String[] args) {
        // t1等待t2的结果
        zhong zhong = new zhong();
        Thread t1 = new Thread(()->{
            log.debug("等待结果");
            Object o = zhong.get();
//            int res = (int)o;
            log.debug("最后的结果是{}",o);
        },"t1");
        t1.start();

        Thread t3 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int sum = 0;
            for (int i = 0; i < 1000; i++) {
                sum += i;
            }
            log.debug("t3设置值");
            zhong.set(sum);
        },"t3");
        t3.start();
    }
}
@Slf4j(topic = "c.zhong")
class zhong{
    Object response;

    public Object get(){
        synchronized (this){
            while (response == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void set(Object response){
        synchronized (this){
                this.response = response;
                this.notifyAll();
        }
    }
}
