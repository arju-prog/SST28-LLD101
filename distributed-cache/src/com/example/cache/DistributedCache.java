package com.example.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * A distributed, in-memory cache that spreads keys across multiple
 * {@link CacheNode}s using a pluggable {@link DistributionStrategy}.
 *
 * On a cache miss during {@link #get(String)}, the value is fetched from the
 * backing {@link Database}, stored in the appropriate cache node, and returned.
 */
public class DistributedCache {

    private final List<CacheNode<String, String>> nodes;
    private DistributionStrategy distributionStrategy;
    private final Database database;

    /**
     * Creates a distributed cache.
     *
     * @param numberOfNodes        how many cache nodes to create
     * @param capacityPerNode      maximum entries each node can hold
     * @param evictionPolicyFactory factory that creates a fresh eviction policy per node
     * @param distributionStrategy strategy for mapping keys to nodes
     * @param database             backing data store for cache-miss lookups
     */
    public DistributedCache(int numberOfNodes,
                            int capacityPerNode,
                            EvictionPolicyFactory<String> evictionPolicyFactory,
                            DistributionStrategy distributionStrategy,
                            Database database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode<>(capacityPerNode, evictionPolicyFactory.create()));
        }
    }

    /**
     * Retrieves a value by key.
     * <ol>
     *   <li>Determine the target node via the distribution strategy.</li>
     *   <li>If the node has the value, return it (cache hit).</li>
     *   <li>Otherwise, fetch from the database, store in the node, and return.</li>
     * </ol>
     *
     * @param key the lookup key
     * @return the value, or null if not found even in the database
     */
    public String get(String key) {
        int nodeIndex = distributionStrategy.getNode(key, nodes.size());
        CacheNode<String, String> node = nodes.get(nodeIndex);

        String value = node.get(key);
        if (value != null) {
            System.out.println("  Cache HIT  -> node " + nodeIndex + " | key=" + key);
            return value;
        }

        // Cache miss — fetch from database
        System.out.println("  Cache MISS -> node " + nodeIndex + " | key=" + key + " | fetching from DB...");
        value = database.get(key);
        if (value != null) {
            node.put(key, value);
        }
        return value;
    }

    /**
     * Stores a key-value pair in the cache (write-through to the appropriate node).
     *
     * @param key   the cache key
     * @param value the value to cache
     */
    public void put(String key, String value) {
        int nodeIndex = distributionStrategy.getNode(key, nodes.size());
        System.out.println("  PUT        -> node " + nodeIndex + " | key=" + key);
        nodes.get(nodeIndex).put(key, value);
    }

    /** Allows swapping the distribution strategy at runtime (e.g., for live migration). */
    public void setDistributionStrategy(DistributionStrategy strategy) {
        this.distributionStrategy = strategy;
    }

    /** Returns the number of cache nodes. */
    public int getNodeCount() {
        return nodes.size();
    }

    // ---- Factory interface for creating eviction policies per node ----

    /**
     * Functional interface for constructing a fresh {@link EvictionPolicy}
     * instance for each cache node.
     *
     * @param <K> the key type
     */
    @FunctionalInterface
    public interface EvictionPolicyFactory<K> {
        EvictionPolicy<K> create();
    }
}
