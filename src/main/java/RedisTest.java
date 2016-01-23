import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RedisTest {

    public static void test(String args[]){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.connect();
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
        System.out.println(jedis.keys("*"));


        RandomObject randomObject = new RandomObject();
        randomObject.setName("Kyle");
        randomObject.setJob("Software Engineer");

        Gson gson = new Gson();
        String json = gson.toJson(randomObject);
        jedis.set("kyle_job", json);

        json = jedis.get("kyle_job");
        System.out.println(json);
        RandomObject object=gson.fromJson(json, RandomObject.class);
        System.out.println(object.getJob());
    }

}
