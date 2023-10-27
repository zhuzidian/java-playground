package zhu;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class RxRobotCallback {

    public static void main(String[] args) {

        test1();

        test2();

        System.out.println("main end");
    }

    static void test1() {
        System.out.println("条件に一致する限る続く");

        Observable.<Integer>create(emitter -> {
                    for (int i = 1; i <= 10; i++) {
                        emitter.onNext(i);
                    }
                })
                .takeWhile(i -> i <= 5)
                .blockingSubscribe(i -> {
                    System.out.println(i);
                });

        System.out.println();
        System.out.println();
    }

    static void test2() {
        System.out.println("条件に一致したらcomplete");

        Observable.<Integer>create(emitter -> {
                    for (int i = 1; i <= 10; i++) {
                        emitter.onNext(i);
                    }
                })
                .takeUntil(i -> i == 8)
                .blockingSubscribe(i -> {
                    System.out.println(i);
                });

        System.out.println();
        System.out.println();
    }
}
