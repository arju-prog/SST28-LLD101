package com.example.ratelimiter;

/**
 * Immutable configuration for a rate limiter.
 *
 * Examples:
 *   100 requests per minute  -> new RateLimitConfig(100, 60_000)
 *   1000 requests per hour   -> new RateLimitConfig(1000, 3_600_000)
 */
public final class RateLimitConfig {

    private final int maxRequests;
    private final long windowSizeInMillis;

    public RateLimitConfig(int maxRequests, long windowSizeInMillis) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("maxRequests must be positive");
        }
        if (windowSizeInMillis <= 0) {
            throw new IllegalArgumentException("windowSizeInMillis must be positive");
        }
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    @Override
    public String toString() {
        return "RateLimitConfig{maxRequests=" + maxRequests
                + ", windowSizeInMillis=" + windowSizeInMillis + "}";
    }
}
