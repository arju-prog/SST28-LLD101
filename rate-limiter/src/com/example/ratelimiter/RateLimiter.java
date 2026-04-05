package com.example.ratelimiter;

/**
 * Strategy interface for rate limiting algorithms.
 * Implementations decide whether a request identified by a key should be allowed.
 *
 * Each key represents an independent rate-limiting bucket (e.g., customer ID,
 * tenant ID, API key, or provider name).
 */
public interface RateLimiter {

    /**
     * Checks whether a request for the given key should be allowed under the
     * current rate limit, and consumes one permit if so.
     *
     * @param key the rate-limiting key (e.g., "tenant:T1", "apikey:xyz")
     * @return true if the request is allowed, false if it should be denied
     */
    boolean allowRequest(String key);
}
