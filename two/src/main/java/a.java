import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;


@Slf4j(topic = "c.a")
public class a {
//    static Object lock = new Object();
     static boolean a = true;
    public static void main(String[] args) {
//        int i = 2;
//        int j = i;
//        Thread t1 = new Thread(() -> {
//            int n = j;
//            synchronized (lock) {
//                while (j == 2) {
//                    try {
//                        log.debug("开始等待");
//                        lock.wait(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//                log.debug("没等到");
//            }
//        }, "t1");
//
//        t1.start();

        Thread thread = new Thread(()->{
            while (a){
//                log.debug("run");
//                System.out.println();
        }
        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            log.debug("end");
            a = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
