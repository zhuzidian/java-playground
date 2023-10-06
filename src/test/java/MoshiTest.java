import com.squareup.moshi.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MoshiTest {
    @Test
    void test1() {
        User user = new User("zhu", 30);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<User> userJsonAdapter = moshi.adapter(User.class);
        String json = userJsonAdapter.toJson(user);
        System.out.println(json);
    }
}

class User {
//    @Json(name = "display_name")
    public String displayName;
    public Integer age;

    public User(String displayName, Integer age) {
        this.displayName = displayName;
        this.age = age;
    }
}