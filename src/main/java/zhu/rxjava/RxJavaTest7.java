package zhu.rxjava;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.internal.observers.BlockingBaseObserver;

public class RxJavaTest7 {

    public static void main(String[] args) throws Exception {
        System.out.println("main start");

        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            // new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 1; i <= 5; i++) {
                    emitter.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println("emitter.onComplete");
                emitter.onComplete();

            // }).start();

            emitter.setCancellable(() -> {
                System.out.println("emitter.setCancellable");
            });
        });
        observable.blockingSubscribe(i -> {
            System.out.println(i);
        });

        System.out.println("main end");
    }
}
