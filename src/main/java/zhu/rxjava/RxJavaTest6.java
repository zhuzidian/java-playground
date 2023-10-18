package zhu.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RxJavaTest6 {

    public static void main(String[] args) throws Exception {
        System.out.println("main start");

        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            new Thread(() -> {
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

            }).start();

            emitter.setCancellable(() -> {
                System.out.println("emitter.setCancellable");
            });
        });

        observable.subscribe(i -> {
            System.out.println(i);
        });

        System.out.println("main end");
    }
}
