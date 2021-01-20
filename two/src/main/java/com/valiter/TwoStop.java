package com.valiter;

import com.Syn.SafeHashTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.concurrent.TimeUnit;

/**
 * 两阶段终止
 */
@Slf4j(topic = "c.TwoStop")
public class TwoStop {
    public static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        TwoStop stop = new TwoStop();
        Thread t1 = new Thread(()->{
            while (true){
                if (flag){
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("一直都在监控");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        TimeUnit.SECONDS.sleep(2);
        log.debug("别监控了");
        stop.stop();
    }
    public  void stop(){
        flag = true;
    }

}
