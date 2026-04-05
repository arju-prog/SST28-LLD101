package com.example.ratelimiter;

/**
 * Facade that internal services use before making external resource calls.
 *
 * This service wraps a {@link RateLimiter} strategy and exposes a simple
 * {@code tryAcquire} method. The underlying algorithm can be swapped
 * without affecting callers.
 */
public class RateLimiterService {

    private final RateLimiter rateLimiter;

    public RateLimiterService(RateLimiter rateLimiter) {
        if (rateLimiter == null) {
            throw new IllegalArgumentException("rateLimiter must not be null");
        }
        this.rateLimiter = rateLimiter;
    }

    /**
     * Attempts to acquire permission for a single request under the given key.
     *
     * @param key rate-limiting key (customer ID, tenant ID, API key, etc.)
     * @return true if the request is within limits and may proceed
     */
    public boolean tryAcquire(String key) {
        return rateLimiter.allowRequest(key);
    }
}
