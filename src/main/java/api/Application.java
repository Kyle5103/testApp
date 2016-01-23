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
//        Jedis jedis = RedisSetup.getConnection();
//        Set<String> keys = jedis.keys("*");
//        for (String key : keys) {
//            jedis.del(key);
//        }
//
//
//
//        for(int i = 0; i < 50; i++){
//            InvoiceResource invoice2 = new InvoiceResource();
//            invoice2.setCompanyName("Comfort Air");
//            invoice2.setInvoiceNo("100" + i);
//            invoice2.setStatus("Incomplete");
//            invoice2.setPrice("41.22");
//            invoice2.setTypeOfWork("Mechanical");
//            invoice2.setComment("Fixed a light bulb.");
//            new InvoiceWorkflow().createInvoice(invoice2);
//        }
//

        SpringApplication.run(Application.class, args);
    }
}
