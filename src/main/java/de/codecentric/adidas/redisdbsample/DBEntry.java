/*
 * (c) Copyright 2018 codecentric AG
 */
package de.codecentric.adidas.redisdbsample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
@Entity
@Table(name = "entries")
public class DBEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "col_key")
    private String key;

    @Column(name = "col_value")
    private String value;

    public DBEntry() {
    }

    public DBEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBEntry dbEntry = (DBEntry) o;
        return Objects.equals(id, dbEntry.id) &&
                Objects.equals(key, dbEntry.key) &&
                Objects.equals(value, dbEntry.value);
    }

    @Override
    public String toString() {
        return "DBEntry{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
