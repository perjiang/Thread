package com.join;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 生产者消费者模型
 */
@Slf4j(topic = "c.twoPeople")
public class twoPeople {
    public static void main(String[] args) {
        vacter vacter = new vacter(2);
        for (int i = 0; i < 5; i++) {
            int id = i;
            new Thread(() -> {
                vacter.set(new Mess(id , "值"+id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    vacter.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "消费者").start();
    }

}
@Slf4j(topic = "c.vacter")
class vacter{
    int leng;
    public vacter(int leng){
        this.leng = leng;
    }
    LinkedList<Mess> list =  new LinkedList<Mess>();
    public void get(){
        synchronized (list){
            while (list.isEmpty()){  // 如果list里面已经没有元素了，消费者就等待
                try {
                    log.debug("队列为空消费者等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            Mess message =  list.removeFirst();
            log.debug("已消费消息 {}", message);  // 消费者消耗了之后通知生产者
           list.notifyAll();


        }
    }

    public void set(Mess message){
        synchronized (list){
            while (list.size() == leng){  // 如果list已经达到设置好的上限，生产者就不能在生产
                try {
                    log.debug("队列已满, 生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            log.debug("已生产消息 {}", message);  // 生产者生产好物品通知消费者使用
            list.notifyAll();


        }
    }

}


final class Mess {
    private int id;
    private Object value;

    public Mess(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}