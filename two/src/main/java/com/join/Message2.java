package com.join;

import lombok.extern.slf4j.Slf4j;

import java.time.Period;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.Message2")
public class Message2 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new PostMan2(i,"这里是内容是"+i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Integer id : MailBox2.ids()) {
            new Person2(id).start();
        }

    }
}

@Slf4j(topic = "c.Person")
class Person2 extends Thread{
    int id;
    public Person2(int id){
        this.id = id;
    }
    @Override
    public synchronized void run() {
        //收信
        Gandeage2 mail = MailBox2.getMail(id);
        log.debug("开始收信");
        Object response = mail.get(5000);
        log.debug("守信完成，id是{},内容是{}",mail.getId(),response);
    }
}

@Slf4j(topic = "c.PostMan")
class PostMan2 extends Thread{
    int id;
    String message;
    public PostMan2(int id,String message){
        this.id = id;
        this.message = message;
    }

    @Override
    public void run() {
        Gandeage2 mail = MailBox2.createMail(id);
        log.debug("开始送信{}",mail.getId());
        mail.set(message);
        log.debug("完成送信{}",mail.getId());
    }
}

class MailBox2{
    private static Map<Integer,Gandeage2> boxes = new Hashtable();
    private static int id = 0;



    public static  Gandeage2  createMail(int id){
        Gandeage2 Gandeage2 = new Gandeage2(id);  // 这是信
        boxes.put(Gandeage2.getId(),Gandeage2);
        return Gandeage2;
    }

    public synchronized static Gandeage2 getMail(int id){  //用户获取到信就从信箱拿出去
        return boxes.remove(id);
    }

    public synchronized static Set<Integer> ids(){
        return boxes.keySet();
    }

}


class Gandeage2{

    private int id;  // 信id
    private Object response; // 信内容
    public Gandeage2(int id){
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
