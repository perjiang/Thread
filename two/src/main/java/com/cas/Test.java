package com.cas;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class Test {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(2);
//        integer.updateAndGet((var)->{
//            return var*5;
//        });
//        System.out.println(integer);

//        while (true){
//            int old = integer.get();
//            int news = old*5;
//            if (integer.compareAndSet(old,news))
//                break;
//        }
//        System.out.println(integer);
        System.out.println(updateandget(integer, p -> p * 5));
    }

    public static Integer updateandget(AtomicInteger integer, IntUnaryOperator intUnaryOperator){
        while (true){
            int old = integer.get();
            int news = intUnaryOperator.applyAsInt(old);
            if (integer.compareAndSet(old,news)){
                return news;
            }
        }
    }
}
