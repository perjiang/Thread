package com.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 测试原子累加器
 */


public class add {

    public static void main(String[] args) {
        demo(()->new AtomicLong(0),(index)->index.getAndIncrement(),1);

        demo(()->new LongAdder(),adder->adder.add(1l),2);
    }


    public static<T> void demo(Supplier<T>supplier, Consumer<T> consumer,int number){
        T add = supplier.get();
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(()->{
                for (int i1 = 0; i1 < 50000; i1++) {
                    consumer.accept(add);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(t->t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(add + " cost:" + (end - start) / 1000_000+"number"+number);
    }
}
