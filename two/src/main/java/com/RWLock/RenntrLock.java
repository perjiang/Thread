package com.RWLock;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
@Slf4j(topic = "c.RenntrLock")
public class RenntrLock {
    public static void main(String[] args) throws InterruptedException {
        DataContainer container = new DataContainer(45);
//        new Thread(()->{
//            container.write();
//        },"t1").start();
//        new Thread(()->{
//            container.write();
//        },"t2").start();
//        new Thread(()->{
//            container.rw();
//        },"t3").start();

//        new Thread(()->{
//            container.wr();
//        },"t4").start();

        CatchDate catchDate = new CatchDate(45);

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            catchDate.setChange(true);
        },"t0").start();

        new Thread(()->{
            catchDate.use();
        },"t5").start();


    }
}

@Slf4j(topic = "c.DataContainer")
class DataContainer{
    public DataContainer(Object data){this.data=data;}
    private Object data = new Object();
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock read = rw.readLock();
    private ReentrantReadWriteLock.WriteLock write = rw.writeLock();

    // 读取操作
    public Object read(){
        log.debug("尝试获取读锁");
        read.lock();
        try {
            log.debug("获取到读锁");
            TimeUnit.SECONDS.sleep(2);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            read.unlock();
            log.debug("释放掉读锁");
        }
        return "error";
    }


    public void write(){
        log.debug("尝试获取写锁");
        write.lock();
        try {
            log.debug("获取到写锁");
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            write.unlock();
            log.debug("释放掉写锁");
        }
    }
    public void rw(){
        read.lock();
        try {
            log.debug("read");
            write.lock();
            log.debug("write");
            try {
                log.debug("{}",data);
            } finally {
            write.unlock();
        }}finally{
            read.unlock();
        }
    }

    public void wr(){
        write.lock();
        try {
            log.debug("write");
            read.lock();
            data = 1111;
            log.debug("red");
            try {
                log.debug("{}",data);
            }finally {
                read.unlock();
            }
        }finally{
              write.unlock();
        }
    }


    public static void sleeper(int s) {
        try {
            TimeUnit.SECONDS.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


@Slf4j(topic = "c.CatchDate")
class CatchDate{
    private Object data;
    private volatile  boolean change = false; // 为假表示没有被改变

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    public CatchDate(Object data){
        this.data = data;
    }

    void use(){
        rw.readLock().lock();
        if (change == true){
            rw.readLock().unlock();
            rw.writeLock().lock();
            try {
                if (change == true){
                    data = 123456;
                    change = false;
                }
                rw.readLock().lock();
                log.debug("数据被改变");
            }finally {
                rw.writeLock().unlock();
            }
        }
        try {
            log.debug("最后的数据是{}",data);
        }finally {
            rw.readLock().unlock();
        }
    }
}
