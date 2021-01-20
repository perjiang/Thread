package com.unChange;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 不可变类保证线程安全
 */
@Slf4j(topic = "c.canChange")
public class canChange {
    public static void main(String[] args) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    log.debug("{}",sdf.parse("2000-12-02"));
                } catch (ParseException e) {
                   log.error("{}",e);
                }
            }).start();
        }

        DateTimeFormatter dtm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                TemporalAccessor parse = dtm.parse("2000-12-02");
                log.debug("{}",parse);
            }).start();
        }

    }
}
