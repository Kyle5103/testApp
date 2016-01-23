package api.redis;

import redis.clients.jedis.Jedis;

public class RedisSetup {

    public static Jedis getConnection(){
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.connect();
        if(!jedis.ping().equals("PONG")){
            throw new RuntimeException("Unable to get connection to database");
        }

        return jedis;
    }
}
