package de.codecentric.adidas.redisdbsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisDbSampleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDbSampleApplication.class);


    public RedisDbSampleApplication(Environment environment) {
        LOGGER.info("redis host: {}", environment.getProperty("spring.redis.host"));
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisDbSampleApplication.class, args);
    }
}
