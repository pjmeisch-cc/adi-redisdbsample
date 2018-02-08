package de.codecentric.adidas.redisdbsample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EntryController.class)
public class EntryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EntryRepository entryRepository;

    @MockBean
    StringRedisTemplate stringRedisTemplate;

    @MockBean
    ValueOperations<String, String> valueOperations;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void storeEntry() throws Exception {
        Entry entry = new Entry("hello", "world");
        DBEntry dbEntry = new DBEntry("hello", "world");

        mockMvc.perform(post("/put")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(entry)))
                .andExpect(status().isOk());

        verify(entryRepository).save(dbEntry);
    }

    @Test
    public void getFromRedis() throws Exception {
        Entry entry = new Entry("hello", "world");

        when(stringRedisTemplate.hasKey("hello")).thenReturn(true);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("hello")).thenReturn("world");


        mockMvc.perform(get("/get/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("from cache"))
                .andExpect(jsonPath("$.entry").value(entry));
    }

    @Test
    public void getFromDatabase() throws Exception {
        Entry entry = new Entry("hello", "world");
        DBEntry dbEntry = new DBEntry("hello", "world");
        dbEntry.setId(1);

        when(stringRedisTemplate.hasKey("hello")).thenReturn(false);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(entryRepository.findByKey("hello")).thenReturn(Optional.of(dbEntry));

        mockMvc.perform(get("/get/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("from database"))
                .andExpect(jsonPath("$.entry").value(entry));

        verify(valueOperations).set("hello", "world");
    }
}
