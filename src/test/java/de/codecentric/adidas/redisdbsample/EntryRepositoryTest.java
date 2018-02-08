package de.codecentric.adidas.redisdbsample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EntryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntryRepository entryRepository;

    @Test
    public void findByExistingKey() {
        final DBEntry dbEntry = new DBEntry("hello", "world");
        entityManager.persist(dbEntry);

        final Optional<DBEntry> optionalDbEntry = entryRepository.findByKey("hello");

        assertThat(optionalDbEntry).isNotNull();
        assertThat(optionalDbEntry.isPresent()).isTrue();
        assertThat(optionalDbEntry.get().getKey()).isEqualToIgnoringCase("hello");
        assertThat(optionalDbEntry.get().getValue()).isEqualToIgnoringCase("world");
    }

    @Test
    public void findByNotExistingKey() {

        final Optional<DBEntry> optionalDbEntry = entryRepository.findByKey("hello");

        assertThat(optionalDbEntry).isNotNull();
        assertThat(optionalDbEntry.isPresent()).isFalse();
    }
}
