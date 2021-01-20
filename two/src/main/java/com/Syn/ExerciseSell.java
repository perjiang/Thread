package com.Syn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * 多线程卖票
 */
@Slf4j(topic = "c.ExerciseSell")
public class ExerciseSell {

    public static void main(String[] args) throws InterruptedException {
        TicketWindow window = new TicketWindow(2000);

        List<Integer> amountList = new Vector<>();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(()->{

                    int sell = window.sell(randomAmont()*300);
                    amountList.add(sell);
                try {
                    Thread.sleep(randomAmont());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            threadList.add(thread);
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        log.debug("余票:{}",window.getCount());
        log.debug("卖出去的票:{}",amountList.stream().mapToInt(i->i).sum());


    }

    static Random random = new Random();

    public static int randomAmont(){
        return random.nextInt(5)+1;
    }


}
class  TicketWindow{
    private int count;

    public TicketWindow(int count){
        this.count = count;
    }

    // 获取余票
    public int getCount(){
        return count;
    }

    //售票
    public  synchronized  int sell(int amont){
                                     // 假如方法没加锁   一共10张票  两个线程抢 每个人都买9张   当线程1进来完了了if判断 失去时间片 在线程1看来 count=10
                                     // 这个时候时间片分给线程2  线程2看到count=10 于是买走9张票 这个时候count实际之后1张
                                     // 线程2执行完毕 线程1继续执行  线程一仍然以为现在还有10张票 继续买走9张  程序出错
        if (this.count >= amont){    // 同步方法同时只有一个线程能够执行  上一个线程执行完 新线程获取新的count 再次判断 在执行。  保证了线程安全
            this.count-=amont;
            return amont;
        }else {
            return 0;
        }
    }

}