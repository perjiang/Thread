package com.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe对象的使用
 */

public class UnSafes {

    public static void main(String[] args)throws Exception {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);
        System.out.println(unsafe);
        Teacher t = new Teacher();
        Thread t1 = new Thread(() -> {
            try {
                long idoffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
                long nameoffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));
                unsafe.compareAndSwapInt(t, idoffset, 0, 1);
                unsafe.compareAndSwapObject(t, nameoffset, null, "t1");
                System.out.println(t.toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                long idoffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
                long nameoffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));
                unsafe.compareAndSwapInt(t, idoffset, 0, 1);
                unsafe.compareAndSwapObject(t, nameoffset, null, "t2");
                System.out.println(t.toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }, "t2");
        t2.start();

    }

}

class Teacher{
    volatile int id;
    volatile String name;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
