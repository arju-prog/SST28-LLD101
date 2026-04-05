package com.example.cache;

/**
 * Strategy for determining which cache node a given key should be routed to.
 * Implementations can use modulo hashing, consistent hashing, or any custom scheme.
 */
public interface DistributionStrategy {

    /**
     * Returns the index of the node (0-based) that should store the given key.
     *
     * @param key           the cache key
     * @param numberOfNodes total number of cache nodes available
     * @return node index in [0, numberOfNodes)
     */
    int getNode(String key, int numberOfNodes);
}
