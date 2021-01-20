package com.jx;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class aa {
     static Set<Integer> set = new HashSet<>();
     static {
         for (int i = 0; i < 100; i++) {
             set.add(i);
         }
     }
    public static void main(String[] args) throws InterruptedException {
//        for (Integer integer : set) {
//            new Thread(()->{
//                System.out.println(integer);
//            }).start();
//        }

        Scanner in = new Scanner(System.in);
        int i = in.nextInt();
        switch (i){
            case 1:
                System.out.println("1");
            case 2:
                System.out.println("2");
            case 3:
                System.out.println("3");
            default:
                System.out.println("de");
        }

    }
}
