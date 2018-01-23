/*
 * (c) Copyright 2018 codecentric AG
 */
package de.codecentric.adidas.redisdbsample;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
public interface EntryRepository extends CrudRepository<DBEntry, Integer> {
    Optional<DBEntry> findByKey(final String key);
}
