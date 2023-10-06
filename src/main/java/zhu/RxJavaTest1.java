package zhu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RxJavaTest1 {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("192.168.10.161", 6379)) {
            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    super.onMessage(channel, message);
                    System.out.println("channel:" + channel);
                    System.out.println("message:" + message);
                }

                @Override
                public void onSubscribe(String channel, int subscribedChannels) {
                    super.onSubscribe(channel, subscribedChannels);
                    System.out.println("onSubscribe:" + channel);
                }

                @Override
                public void onUnsubscribe(String channel, int subscribedChannels) {
                    super.onUnsubscribe(channel, subscribedChannels);
                    System.out.println("onUnsubscribe:" + channel);
                }
            };
            jedis.subscribe(jedisPubSub, "robot");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
