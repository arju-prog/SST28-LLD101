package com.example.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory implementation of {@link Database} backed by a HashMap.
 * Used for demonstration and testing purposes.
 */
public class InMemoryDatabase implements Database {

    private final Map<String, String> store = new HashMap<>();

    public void put(String key, String value) {
        store.put(key, value);
    }

    @Override
    public String get(String key) {
        return store.get(key);
    }
}
