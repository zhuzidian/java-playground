package zhu.old;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RxJavaTest8 {

    private static int counter = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("main start");

        new Thread(() -> {
            while (counter < 10) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                counter ++;
            }
        }).start();

        Observable.<Integer>create(emitter -> {
            emitter.onNext(counter);

            if (counter == 5) {
                emitter.onComplete();
            }
        }).subscribe(i -> {
            System.out.println(i);
        });

        System.out.println("main end");
    }
}
