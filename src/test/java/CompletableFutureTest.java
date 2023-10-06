import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

//import static org.junit.jupiter.api.Assertions.fail;

public class CompletableFutureTest {
    @Test
    void test1() throws Exception {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("cf1");
            System.out.println(LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "こんにちは";
        });

        CompletableFuture<String> cf2 = cf1.thenApplyAsync((result) -> {
            System.out.println("cf2");
            System.out.println(LocalDateTime.now());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return result + " 世界";
        });

        cf2.thenAccept((result) -> {
            System.out.println("result: " + result);
        });

        Thread.sleep(5000);
    }
}
