/*
 * (c) Copyright 2018 codecentric AG
 */
package de.codecentric.adidas.redisdbsample;

import java.util.Objects;

/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
public class Entry {
    private String key;
    private String value;

    public Entry() {
    }

    public Entry(String key, String value) {
        this.key = key;
        this.value = value;
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

        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(key, entry.key) &&
                Objects.equals(value, entry.value);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
