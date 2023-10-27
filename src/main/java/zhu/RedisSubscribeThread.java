package zhu;

import java.util.concurrent.atomic.AtomicBoolean;

public class RedisSubscribeThread {
    public static void main(String[] args) throws Exception {
        AtomicBoolean runFlag = new AtomicBoolean(true);

        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            runFlag.set(false);
        }, "a").start();

        new Thread(() -> {
            while (runFlag.get()) {
                System.out.println(runFlag.get());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "b").start();
    }
}
