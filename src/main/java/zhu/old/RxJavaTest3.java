package zhu.old;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

public class RxJavaTest3 {
    public static void main(String[] args) {
//        Flowable<JedisPubSub> jedisPubSubFlowable = Flowable.create(emitter -> {
//            try (JedisPooled jedis = new JedisPooled("192.168.10.161", 6379)) {
//                JedisPubSub jedisPubSub = new JedisPubSub() {
//                    @Override
//                    public void onMessage(String channel, String message) {
//                        System.out.println("JedisPubSub.onMessage channel: " + channel + " message: " + message);
//                        emitter.onComplete();
//                    }
//
//                    @Override
//                    public void onSubscribe(String channel, int subscribedChannels) {
//                        // emitter.onNext(this);
//                        System.out.println("JedisPubSub.onSubscribe: " + channel);
//                        // サブスクができたら、requestを送信
//                        try (JedisPooled jedis = new JedisPooled("192.168.10.161", 6379)) {
//                            jedis.publish("CALL_ELEVATOR_REQUEST", "hello");
//                        }
//                    }
//
//                    @Override
//                    public void onUnsubscribe(String channel, int subscribedChannels) {
//                        // emitter.onNext(this);
//                    }
//                };
//                emitter.setCancellable(() -> {
//                    // サブスクを解除
//                    System.out.println("jedisPubSub.unsubscribe()");
//                    jedisPubSub.unsubscribe();
//                });
//
//                // requestを送る前、ACK_RESPONSEをサブスク
//                jedis.subscribe(jedisPubSub, "ACK_RESPONSE");
//            }
//        }, BackpressureStrategy.BUFFER);
//
//
//        Subscriber<JedisPubSub> subscriber = new DisposableSubscriber<>() {
//            @Override
//            public void onNext(JedisPubSub jedisPubSub) {
//                this.cancel();
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        jedisPubSubFlowable.subscribeWith(subscriber);

        System.out.println(1);
        Disposable d =
                Flowable.range(1, 5)
                        .delay(10, TimeUnit.SECONDS)
                        .subscribeWith(new DisposableSubscriber<Integer>() {
//                            @Override public void onStart() {
//                                request(1);
//                            }
                            @Override public void onNext(Integer t) {
                                if (t == 3) {
                                    cancel();
                                }
                                System.out.println(t);
//                                request(1);
                            }
                            @Override public void onError(Throwable t) {
                                t.printStackTrace();
                            }
                            @Override public void onComplete() {
                                System.out.println("Done!");
                            }
                        });
        System.out.println(2);
//        d.dispose();
        System.out.println(3);

        System.out.println("done");
    }
}
