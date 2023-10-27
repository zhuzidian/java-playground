package zhu.old;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.JedisPubSub;

public class RxJavaTest2 {
    public static void main(String[] args) {
        Observable<JedisPubSub> jedisPubSubObservable = Observable.create(emitter -> {
            // subscribe ack
            try (JedisPooled jedis = new JedisPooled("192.168.10.161", 6379)) {
                JedisPubSub jedisPubSub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println("JedisPubSub.onMessage channel: " + channel + " message: " + message);
                        emitter.onComplete();
                    }

                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        // emitter.onNext(this);
                        System.out.println("JedisPubSub.onSubscribe: " + channel);
                        // サブスクができたら、requestを送信
                        try (JedisPooled jedis = new JedisPooled("192.168.10.161", 6379)) {
                            jedis.publish("CALL_ELEVATOR_REQUEST", "hello");
                        }
                    }

                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {
                        // emitter.onNext(this);
                    }
                };
                emitter.setCancellable(() -> {
                    // サブスクを解除
                    System.out.println("jedisPubSub.unsubscribe()");
                    jedisPubSub.unsubscribe();
                });

                // requestを送る前、ACK_RESPONSEをサブスク
                jedis.subscribe(jedisPubSub, "ACK_RESPONSE");
            }
        });

        jedisPubSubObservable.subscribe(
                (jedisPubSub) -> {},
                (t) -> {},
                () -> {}
        );

        jedisPubSubObservable.subscribeWith(new Observer<JedisPubSub>() {
            private Disposable d;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Observer.onSubscribe: " + d);
                this.d = d;
            }

            @Override
            public void onNext(@NonNull JedisPubSub jedisPubSub) {
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("Observer.onComplete");
                // サブスクを解除
                this.d.dispose();
            }
        });

        System.out.println("done");
    }
}
