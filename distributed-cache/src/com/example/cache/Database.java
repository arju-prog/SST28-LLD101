package com.example.cache;

/**
 * Abstraction for the backing data store.
 * On a cache miss the distributed cache falls back to this source of truth.
 */
public interface Database {

    /**
     * Retrieves the value associated with the given key from the database.
     *
     * @param key the lookup key
     * @return the value, or null if the key does not exist
     */
    String get(String key);
}
