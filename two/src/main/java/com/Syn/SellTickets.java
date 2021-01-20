package com.Syn;

import jdk.jfr.StackTrace;
import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;

/**
 * 非线程安全的计算
 */
@Slf4j(topic = "c.Count")

public class SellTickets {
    public static void main(String[] args) {

        //创建车票对象,并初始化车票总数
        Ticket ticket=new Ticket(1000);

        //创建售票线程 ,并设置窗口名字,然后启动线程,这里设置四个窗口
        new Thread(ticket,"窗口A").start();
        new Thread(ticket,"窗口B").start();
        new Thread(ticket,"窗口C").start();
        new Thread(ticket,"窗口D").start();

    }
}

/**
 * 采用实现Runnable接口的方式实现多线程
 */
class Ticket implements Runnable {

    //总的车票数
    int total;

    Ticket(int total){
        this.total=total;
    }

    //初始车票号码 1
    int num = 1;

    @Override
    public void run() {
        while (true) {

            synchronized ("") {
                try {
                    // 任何线程获取"线程锁"以后都要先判断是否还有余票,防止等待的线程多打印车票
                    if (num > total)            return;

                    // 获取当前线程名字
                    String threadName = Thread.currentThread().getName();

                    // 格式化票号
                    String ticketNum = FormatTicketNum(num++);

                    // 打印火车票,休眠20毫秒模拟打印车票时间
                    Thread.sleep(20);
                    System.out.println(threadName + " 售出火车票No." + ticketNum);

                    // 某线程售完最后一张车票时,放出"车票已售罄"提示
                    if (num > total) {
                        System.out.println("车票已售罄!");
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 格式化车票号码
     */
    static String FormatTicketNum(int num) {

        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMinimumIntegerDigits(3);
        return nf.format(num);
    }
}