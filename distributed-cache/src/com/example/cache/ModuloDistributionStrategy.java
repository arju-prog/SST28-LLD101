package com.example.cache;

/**
 * Simple distribution strategy that maps a key to a node using
 * {@code Math.abs(key.hashCode()) % numberOfNodes}.
 *
 * Fast and straightforward, but adding/removing nodes causes most keys
 * to be remapped. For minimal remapping see {@link ConsistentHashingStrategy}.
 */
public class ModuloDistributionStrategy implements DistributionStrategy {

    @Override
    public int getNode(String key, int numberOfNodes) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException("numberOfNodes must be positive");
        }
        return Math.abs(key.hashCode()) % numberOfNodes;
    }
}
