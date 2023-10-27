package zhu.old;

import io.reactivex.rxjava3.core.Observable;

public class RxJavaTest7 {

    public static void main(String[] args) throws Exception {
        System.out.println("main start");

        Observable.<Integer>create(emitter -> {
            new Thread(() -> {
                for (int i = 1; i <= 5; i++) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    emitter.onNext(i);
                }
                System.out.println("emitter.onComplete");
                emitter.onComplete();
            }).start();

            System.out.println(11);
            emitter.setCancellable(() -> {
                System.out.println("emitter.setCancellable");
            });
            System.out.println(22);

        }).blockingSubscribe(i -> {
            System.out.println(i);
        });

        System.out.println("main end");
    }
}
