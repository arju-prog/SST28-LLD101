package com.example.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Least-Recently-Used eviction policy.
 * Uses a doubly-linked list and a hash map to achieve O(1) for both
 * {@link #keyAccessed(Object)} and {@link #evict()}.
 *
 * @param <K> the type of keys tracked by this policy
 */
public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {

    // ---- internal doubly-linked-list node ----
    private static class Node<K> {
        K key;
        Node<K> prev;
        Node<K> next;

        Node(K key) {
            this.key = key;
        }
    }

    // Sentinel head (least recently used end) and tail (most recently used end).
    private final Node<K> head;
    private final Node<K> tail;
    private final Map<K, Node<K>> map;

    public LRUEvictionPolicy() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
        map = new HashMap<>();
    }

    /**
     * Marks the key as most-recently-used.
     * If the key is already tracked it is moved to the tail;
     * otherwise a new node is inserted at the tail.
     */
    @Override
    public void keyAccessed(K key) {
        if (map.containsKey(key)) {
            // detach existing node
            Node<K> node = map.get(key);
            detach(node);
            attachToTail(node);
        } else {
            Node<K> node = new Node<>(key);
            attachToTail(node);
            map.put(key, node);
        }
    }

    /**
     * Evicts the least-recently-used key (node right after the head sentinel).
     *
     * @return the evicted key, or null if no keys are tracked
     */
    @Override
    public K evict() {
        if (head.next == tail) {
            return null; // nothing to evict
        }
        Node<K> lru = head.next;
        detach(lru);
        map.remove(lru.key);
        return lru.key;
    }

    // ---- linked-list helpers ----

    private void detach(Node<K> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void attachToTail(Node<K> node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }
}
