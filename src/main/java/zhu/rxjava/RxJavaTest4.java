package zhu.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

import java.util.concurrent.TimeUnit;

public class RxJavaTest4 {
    public static void main(String[] args) throws InterruptedException {
        Observable.just(10L, 1L)
                .flatMap(x ->
                        Observable.just(x).delay(x, TimeUnit.SECONDS))
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(15);
    }
}
