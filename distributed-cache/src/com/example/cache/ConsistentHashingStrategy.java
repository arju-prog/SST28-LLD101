package com.example.cache;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Distribution strategy based on consistent hashing with virtual nodes.
 *
 * Each physical node is mapped to {@code virtualNodeCount} positions on a
 * hash ring. A key is routed to the first node whose ring position is >= the
 * key's hash (clockwise walk). This minimises key remapping when nodes are
 * added or removed.
 */
public class ConsistentHashingStrategy implements DistributionStrategy {

    private final int virtualNodeCount;

    /**
     * @param virtualNodeCount number of virtual nodes per physical node.
     *                         Higher values give more uniform distribution.
     */
    public ConsistentHashingStrategy(int virtualNodeCount) {
        if (virtualNodeCount <= 0) {
            throw new IllegalArgumentException("virtualNodeCount must be positive");
        }
        this.virtualNodeCount = virtualNodeCount;
    }

    /** Convenience constructor that defaults to 100 virtual nodes per physical node. */
    public ConsistentHashingStrategy() {
        this(100);
    }

    @Override
    public int getNode(String key, int numberOfNodes) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException("numberOfNodes must be positive");
        }

        // Build the ring for the current number of nodes.
        // In a production system this would be cached and updated incrementally;
        // here we rebuild for simplicity since this is an in-memory LLD demo.
        SortedMap<Integer, Integer> ring = new TreeMap<>();
        for (int node = 0; node < numberOfNodes; node++) {
            for (int v = 0; v < virtualNodeCount; v++) {
                int hash = hash("node-" + node + "-vn-" + v);
                ring.put(hash, node);
            }
        }

        int keyHash = hash(key);

        // Walk clockwise: find the first ring position >= keyHash
        SortedMap<Integer, Integer> tailMap = ring.tailMap(keyHash);
        int ringPosition = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        return ring.get(ringPosition);
    }

    /**
     * Simple hash function that produces a well-distributed int.
     * Uses FNV-1a-inspired mixing for better spread than {@code String.hashCode()}.
     */
    private int hash(String value) {
        int h = 0x811c9dc5; // FNV offset basis
        for (int i = 0; i < value.length(); i++) {
            h ^= value.charAt(i);
            h *= 0x01000193; // FNV prime
        }
        return h;
    }
}
