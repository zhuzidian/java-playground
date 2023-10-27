package zhu;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Objects;

public class RxRedisCallback {

    private static JedisPubSub pubSub = null;

    private static void publish(String channel, String message) {
        try (Jedis c = new Jedis("192.168.10.161", 6379)) {
            c.publish(channel, message);
        }
    }

    private static void subscribe(JedisPubSub pubSub, String channel) {
        new Thread(() -> {
            try (Jedis c = new Jedis("192.168.10.161", 6379)) {
                c.subscribe(pubSub, channel);
            }
        }).start();
    }

    public static Observable<Object> subscribeCallbackObservable(String channel) {
        return Observable.<Object>create(emitter -> {
            var pubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    super.onMessage(channel, message);
                    System.out.println("onMessage: " + message);
                    emitter.onNext("MESSAGE");
                }

                @Override
                public void onSubscribe(String channel, int subscribedChannels) {
                    super.onSubscribe(channel, subscribedChannels);
                    System.out.println("onSubscribe");
                    emitter.onNext("SUBSCRIBE");
                }

                @Override
                public void onUnsubscribe(String channel, int subscribedChannels) {
                    super.onUnsubscribe(channel, subscribedChannels);
                    System.out.println("onUnsubscribe");
                    emitter.onNext("UNSUBSCRIBE");
                    emitter.onComplete();
                }
            };

            emitter.setCancellable(() -> {
                System.out.println("setCancellable");
            });

            emitter.onNext(pubSub);

            subscribe(pubSub, channel);
        });
    }

    public static void main(String[] args) {
        System.out.println("main start...");

        subscribeCallbackObservable("temi/ack")
                .blockingSubscribe(it -> {
                    System.out.println("---" + it);

                    if (it instanceof JedisPubSub) {
                        pubSub = (JedisPubSub) it;
                    }

                    if (Objects.equals(it, "SUBSCRIBE")) {
                        System.out.println("publish");
                        publish("temi/request", "hello");
                    }

                    if (Objects.equals(it, "MESSAGE")) {
                        System.out.println("get ack");

                        pubSub.unsubscribe();
                    }
                });

        System.out.println("main end...");
    }
}
