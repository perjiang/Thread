package com.Syn;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Hashtable;

/**
 *
 */

@Slf4j(topic = "c.SafeHashTable")
public class SafeHashTable {

    final static Date d1 = new Date();
    Hashtable<String,String> table = new Hashtable<>();

    public void get(String value){
        if (table.get("key")==null){
            log.debug("null");
            table.put("key", value);
            log.debug("{}",value);
        }
    }
    public static void main(String[] args) {
        SafeHashTable sage = new SafeHashTable();
        Thread t1 = new Thread(() -> {
            sage.get("v1");
        }, "v1");
        Thread t2 = new Thread(() -> {
            sage.get("v2");
        }, "v2");

        t1.start();
        t2.start();

    }
}
