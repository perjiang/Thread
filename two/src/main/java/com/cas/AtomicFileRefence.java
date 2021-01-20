package com.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 字段更新器
 */
@Slf4j(topic = "c.AtomicFileRefence")
public class AtomicFileRefence {
    public static void main(String[] args) {
        student stu = new student();
        stu.name = "张三";
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(student.class,String.class,"name");

        updater.compareAndSet(stu,"张三","王二麻子");
        System.out.println(stu.toString());

    }
}

class student{
    volatile String  name;

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                '}';
    }
}
