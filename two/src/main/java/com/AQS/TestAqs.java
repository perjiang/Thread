package com.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义不可重入锁
 */
@Slf4j(topic = "c.TestAqs")
public class TestAqs {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(()->{
            lock.lock();
            log.debug("locking......");
            lock.lock();
            log.debug("locking......");
            try {
                log.debug("locking......");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking....");
                lock.unlock();
            }
        },"t1").start();

//        new Thread(()->{
//            lock.lock();
//            try {
//                log.debug("locking......");
//            }finally {
//                log.debug("unlocking....");
//                lock.unlock();
//            }
//        },"t2").start();
    }
}

class MyLock implements Lock{

    class Mysync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {

            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
//            if (getState()==1 &&  (getExclusiveOwnerThread()==(Thread.currentThread()))){
//                return true;
//            }  // 加上这一段就是可重入锁  如果是有锁状态，判断当前锁的owner是否为当前线程 如果是返回true  实现可重入
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private Mysync mysync = new Mysync();

    @Override
    public void lock() {
        mysync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        mysync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
       return mysync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return mysync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        mysync.release(1);
    }

    @Override
    public Condition newCondition() {
        return mysync.newCondition();
    }
}
