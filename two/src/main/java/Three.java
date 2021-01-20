import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.a")
public class Three {

    static int ticket = 10000000;

    public static void main(String[] args) {
        Three three = new Three();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    while (ticket>0){
                        three.sale();
//                        Thread.sleep(500);
                    }

                }
            });
            thread.start();
        }
    }

    public synchronized void sale(){
        if (ticket>0){
            ticket--;
            log.debug("{}",ticket);
        }else {
            return;
        }
    }
}
