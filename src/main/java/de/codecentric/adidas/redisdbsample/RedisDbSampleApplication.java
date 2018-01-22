package de.codecentric.adidas.redisdbsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisDbSampleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDbSampleApplication.class);

    private final StringRedisTemplate stringRedisTemplate;

    private final Environment environment;

    public RedisDbSampleApplication(StringRedisTemplate stringRedisTemplate,
                                    Environment environment) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.environment = environment;
        LOGGER.info("redis host: {}", environment.getProperty("spring.redis.host"));
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisDbSampleApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        LOGGER.info("test called");

        final ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        final String key = "pjm-test";

        if (!stringRedisTemplate.hasKey(key)) {
            final String value = "success";
            LOGGER.info("setting value {} for key {}", value, key);
            opsForValue.set(key, value);
        }

        LOGGER.info("getting value for key {}", key);
        String value = opsForValue.get(key);
        LOGGER.info("got value {}", value);
        return value;
    }
}
