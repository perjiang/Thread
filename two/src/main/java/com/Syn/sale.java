package com.Syn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j(topic = "c.a")
public class sale implements Runnable {
    static  int tickets = 20;
     static boolean flag = true;

    public static  void s(){
        try {
            if (tickets<0) {
                flag =false;
                return;}
            log.debug("当前线程名{},还剩车票{}",Thread.currentThread().getName(),tickets--);
            Thread.sleep(10);
            if (tickets<0){
                log.debug("没了");
                flag =false;
                return;
            }
        }catch (InterruptedException e) {
        e.printStackTrace();
    }



    }

    @Override
    public void run() {
        while (flag){

            synchronized (""){
                s();
            }


        }
    }

    public static void main(String[] args) {
        sale sale = new sale();
        new Thread(sale,"one").start();
        new Thread(sale,"two").start();
        new Thread(sale,"three").start();
    }
}