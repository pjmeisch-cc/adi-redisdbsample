/*
 * (c) Copyright 2018 codecentric AG
 */
package de.codecentric.adidas.redisdbsample;

/**
 * @author P.J. Meisch (peter-josef.meisch@codecentric.de)
 */
public class Response {
    private final Entry entry;
    private final String message;

    public Response(Entry entry, String message) {
        this.entry = entry;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "entry=" + entry +
                ", message='" + message + '\'' +
                '}';
    }

    public Entry getEntry() {
        return entry;
    }

    public String getMessage() {
        return message;
    }
}
