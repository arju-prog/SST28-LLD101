package com.example.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single cache node with a fixed capacity.
 * When the node is full and a new key is inserted, the configured
 * {@link EvictionPolicy} decides which existing key to remove.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class CacheNode<K, V> {

    private final int capacity;
    private final EvictionPolicy<K> evictionPolicy;
    private final Map<K, V> store;

    public CacheNode(int capacity, EvictionPolicy<K> evictionPolicy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
        this.store = new HashMap<>();
    }

    /**
     * Retrieves the value for the given key from this node.
     *
     * @param key the lookup key
     * @return the cached value, or null on a cache miss
     */
    public V get(K key) {
        if (!store.containsKey(key)) {
            return null;
        }
        evictionPolicy.keyAccessed(key);
        return store.get(key);
    }

    /**
     * Stores a key-value pair in this node.
     * If the node is at capacity and the key is new, the eviction policy
     * is consulted to make room first.
     *
     * @param key   the cache key
     * @param value the value to store
     */
    public void put(K key, V value) {
        if (store.containsKey(key)) {
            // Update existing entry — no eviction needed
            store.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }

        if (store.size() >= capacity) {
            K evictedKey = evictionPolicy.evict();
            if (evictedKey != null) {
                store.remove(evictedKey);
                System.out.println("  [Node] Evicted key: " + evictedKey);
            }
        }

        store.put(key, value);
        evictionPolicy.keyAccessed(key);
    }

    /** Returns the current number of entries in this node. */
    public int size() {
        return store.size();
    }

    /** Returns the maximum capacity of this node. */
    public int getCapacity() {
        return capacity;
    }
}
