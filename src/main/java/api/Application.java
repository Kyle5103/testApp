package api;

import api.models.InvoiceResource;
import api.redis.RedisSetup;
import api.workflows.InvoiceWorkflow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) throws Exception {
        new Application();
        SpringApplication.run(Application.class, args);
    }
}
