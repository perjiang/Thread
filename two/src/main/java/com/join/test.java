package com.join;

public class test {

    static int sum = 0;
    static int end = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                sum+=i;
                System.out.println(sum);
            }
        },"t1");
        t1.start();
        Thread t2 = new Thread(()->{
            end = sum + 10;
            System.out.println(end);
        },"t2");
        t1.join();
        t2.start();
    }

}
