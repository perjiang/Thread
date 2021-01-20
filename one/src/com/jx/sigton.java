package com.jx;

import java.util.concurrent.locks.ReentrantLock;

public class sigton {
    private sigton(){};

    private static class LazyLoader{
        static final sigton SIGTON = new sigton();
    }

    public static sigton getInstance(){
        return LazyLoader.SIGTON;
    }
}
