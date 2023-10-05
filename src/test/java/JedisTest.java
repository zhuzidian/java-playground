import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisTest {
    @Test
    void test_pubsub1() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.subscribe(new JedisPubSubListener(), "channel1");
        }
    }
}

class JedisPubSubListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        System.out.println("JedisPubSubListener onMessage " + channel + message);
    }
}
