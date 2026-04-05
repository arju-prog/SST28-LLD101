package com.example.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Fixed Window Counter rate limiting algorithm.
 *
 * Time is divided into fixed-size windows. Each window maintains a counter
 * that is incremented on every request. When the counter reaches the limit
 * the request is denied. When the current time moves past the window boundary,
 * the counter resets.
 *
 * Thread-safe: uses per-key synchronization so independent keys do not
 * contend with each other.
 */
public class FixedWindowCounter implements RateLimiter {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, WindowState> windows;

    public FixedWindowCounter(RateLimitConfig config) {
        this.config = config;
        this.windows = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String key) {
        WindowState state = windows.computeIfAbsent(key, k -> new WindowState());
        long now = System.currentTimeMillis();

        synchronized (state) {
            long currentWindowStart = getWindowStart(now);

            // If we have moved into a new window, reset the counter.
            if (state.windowStart != currentWindowStart) {
                state.windowStart = currentWindowStart;
                state.counter = 0;
            }

            if (state.counter < config.getMaxRequests()) {
                state.counter++;
                return true;
            }
            return false;
        }
    }

    /**
     * Returns the start timestamp of the window that {@code timestamp} falls into.
     */
    private long getWindowStart(long timestamp) {
        return timestamp - (timestamp % config.getWindowSizeInMillis());
    }

    // -----------------------------------------------------------------------
    // Internal mutable state per key. Access is guarded by synchronizing on
    // the WindowState instance itself.
    // -----------------------------------------------------------------------
    private static class WindowState {
        long windowStart;
        int counter;

        WindowState() {
            this.windowStart = 0;
            this.counter = 0;
        }
    }
}
