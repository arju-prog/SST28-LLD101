package com.example.cache;

/**
 * Demonstrates the distributed cache with:
 *   - 3 cache nodes, capacity 2 each
 *   - LRU eviction policy
 *   - Modulo distribution strategy (then switches to consistent hashing)
 *   - Cache miss -> DB fallback
 *   - Eviction when a node exceeds capacity
 */
public class Main {

    public static void main(String[] args) {

        // ---- Set up an in-memory database with some seed data ----
        InMemoryDatabase db = new InMemoryDatabase();
        db.put("user:1", "Alice");
        db.put("user:2", "Bob");
        db.put("user:3", "Charlie");
        db.put("user:4", "Diana");
        db.put("user:5", "Eve");
        db.put("user:6", "Frank");
        db.put("user:7", "Grace");

        // ---- Create distributed cache: 3 nodes, capacity 2, LRU, modulo ----
        System.out.println("=== Creating Distributed Cache ===");
        System.out.println("Nodes: 3 | Capacity per node: 2 | Eviction: LRU | Strategy: Modulo\n");

        DistributedCache cache = new DistributedCache(
                3,                              // number of nodes
                2,                              // capacity per node
                LRUEvictionPolicy::new,         // eviction policy factory
                new ModuloDistributionStrategy(),// distribution strategy
                db                              // backing database
        );

        // ---- Demonstrate put operations ----
        System.out.println("--- PUT operations ---");
        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob");
        cache.put("user:3", "Charlie");
        cache.put("user:4", "Diana");
        System.out.println();

        // ---- Demonstrate cache hit ----
        System.out.println("--- GET operations (cache hits) ---");
        System.out.println("get(user:1) = " + cache.get("user:1"));
        System.out.println("get(user:2) = " + cache.get("user:2"));
        System.out.println();

        // ---- Demonstrate cache miss -> DB fallback ----
        System.out.println("--- GET operations (cache miss -> DB fallback) ---");
        System.out.println("get(user:5) = " + cache.get("user:5"));
        System.out.println("get(user:6) = " + cache.get("user:6"));
        System.out.println();

        // ---- Demonstrate eviction ----
        System.out.println("--- Eviction demonstration ---");
        System.out.println("Adding more keys to force evictions on nodes at capacity...");
        cache.put("user:7", "Grace");
        System.out.println("get(user:7) = " + cache.get("user:7"));
        System.out.println();

        // ---- Key not in DB ----
        System.out.println("--- GET for a key not in DB ---");
        System.out.println("get(user:99) = " + cache.get("user:99"));
        System.out.println();

        // ---- Switch to Consistent Hashing strategy ----
        System.out.println("=== Switching to Consistent Hashing Strategy ===\n");
        cache.setDistributionStrategy(new ConsistentHashingStrategy(150));

        System.out.println("--- GET operations with Consistent Hashing ---");
        System.out.println("get(user:1) = " + cache.get("user:1"));
        System.out.println("get(user:2) = " + cache.get("user:2"));
        System.out.println("get(user:3) = " + cache.get("user:3"));
        System.out.println();

        System.out.println("--- PUT + GET with Consistent Hashing ---");
        cache.put("user:5", "Eve (updated)");
        System.out.println("get(user:5) = " + cache.get("user:5"));
        System.out.println();

        System.out.println("=== Demo Complete ===");
    }
}
