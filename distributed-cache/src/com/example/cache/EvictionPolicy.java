package com.example.cache;

/**
 * Policy that tracks key access order and decides which key to evict
 * when the cache node reaches capacity.
 *
 * @param <K> the type of keys managed by this policy
 */
public interface EvictionPolicy<K> {

    /**
     * Signals that a key has been accessed (read or written).
     * The policy should update its internal ordering accordingly.
     *
     * @param key the key that was accessed
     */
    void keyAccessed(K key);

    /**
     * Selects and removes the key that should be evicted according to this policy.
     *
     * @return the key to evict, or null if the policy tracks no keys
     */
    K evict();
}
