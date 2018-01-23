/*
 * (c) Copyright 2018 codecentric AG
 */
package de.codecentric.adidas.redisdbsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
@RestController
public class EntryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryController.class);
    private static final Map<String, String> dummyDB = new ConcurrentHashMap<>();
    private final StringRedisTemplate stringRedisTemplate;

    public EntryController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<Response> get(@PathVariable("key") final String key) {
        LOGGER.info("got request for {}", key);

        String message = "from cache";
        final ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        Entry entry;

        if (!stringRedisTemplate.hasKey(key)) {
            LOGGER.info("{} not in cache, search db", key);

            Optional<Entry> optionalEntry = fromDB(key);
            if (!optionalEntry.isPresent()) {
                LOGGER.info("{} not found", key);
                return ResponseEntity.notFound().build();
            }

            message = "from database";
            entry = optionalEntry.get();
            opsForValue.set(entry.getKey(), entry.getValue());
        } else {
            String value = opsForValue.get(key);
            entry = new Entry(key, value);
        }
        
        LOGGER.info("{}: {}", entry, message);
        return ResponseEntity.ok(new Response(entry, message));

    }

    private Optional<Entry> fromDB(String key) {
        String value = dummyDB.get(key);
        return (value != null) ? Optional.of(new Entry(key, value)) : Optional.empty();
    }

    @PostMapping("/put")
    public ResponseEntity<?> store(@RequestBody final Entry entry) {
        LOGGER.info("storing {}", entry);
        dummyDB.put(entry.getKey(), entry.getValue());
        return ResponseEntity.ok().build();
    }
}
