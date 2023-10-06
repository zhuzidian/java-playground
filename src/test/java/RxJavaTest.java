import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class RxJavaTest {
    @Test
    void test_1() {
        Single<Integer> single = Single.create(emitter -> {
            System.out.println("Thread:" + Thread.currentThread().getName());
            emitter.onSuccess(1);
        });

        single.subscribe(new SingleObserver<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                System.out.println("onSuccess");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError");
            }
        });
    }

    @Test
    void test_2() {
        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribeOn(Schedulers.newThread())
                .filter(i -> {
                    System.out.println(i + ":" + Thread.currentThread().getName());
                    return i % 2 == 0;
                })
                .map(i -> {
                    System.out.println(i + ":" + Thread.currentThread().getName());
                    return i * 10;
                })
                .subscribe(new Observer<>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Integer i) {
                        System.out.println("onNext=" + i);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
        System.out.println(Thread.currentThread().getName());
    }

    @Test
    void test_3() {
        Disposable d = Flowable.fromArray(1, 2, 3, 4, 5)
                .map(i -> i * 10)
                .subscribe(i -> System.out.println("i=" + i));
        d.dispose();
    }

    @Test
    void test_4() {
    }
}
