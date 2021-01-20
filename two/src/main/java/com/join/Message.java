package com.join;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.Message")
public class Message {


    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            Person p = new Person();
            p.start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Integer id : MailBox.getIds()) {
            PostMan postMan = new PostMan(id, "内容是" + id);
            postMan.start();
        }

    }
}

@Slf4j(topic = "c.Person")
class Person extends Thread{
    @Override
    public void run() {
        //收信
        Gandeage gandeage = MailBox.createGandeage();
        log.debug("开始收信{}",gandeage.getId());
        Object mail = gandeage.get(5000);
        log.debug("收信{},内容是{}",gandeage.getId(),mail);

    }
}

@Slf4j(topic = "c.PostMan")
class PostMan extends Thread{
    private int id;
    private Object mail;
    public PostMan(int id,Object mail){
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        Gandeage gandeage = MailBox.getGandeage(id);
        log.debug("开始送信{}，送信内容{}",gandeage.getId(),mail);
        gandeage.set(mail);
    }


}

class MailBox{
    private static Map<Integer,Gandeage> boxes = new Hashtable();
    private static int id = 1;

    private static synchronized int gandeageId(){
        return  id++;
    }

    public synchronized static CopyOnWriteArraySet<Integer> getIds(){
        Set<Integer> integers = boxes.keySet();
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        for (Integer integer : integers) {
            set.add(integer);
        }
        return set;
    }

    // 居民需要手的信
    public static Gandeage createGandeage(){
        Gandeage gandeage = new Gandeage(gandeageId());
        boxes.put(gandeage.getId(),gandeage);
        return gandeage;
    }

    // 邮递员送出信
    public static Gandeage  getGandeage(int id){
        return boxes.remove(id);
    }


}


class Gandeage{

    private int id;
    private Object response;
    public Gandeage(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object get(long timieout){
        synchronized (this){
            long start = System.currentTimeMillis();
            long passtime = 0;
            while (response==null){
                long wait = timieout - passtime;
                if (wait<=0){
                    break;
                }
                try {
                    this.wait(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passtime = System.currentTimeMillis()-start;
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
