package com.example.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Sliding Window Counter rate limiting algorithm.
 *
 * This is an approximation of a true sliding window that avoids storing
 * individual timestamps. It works by keeping counters for the current
 * fixed window and the previous fixed window, then computing a weighted
 * count based on how far the current time is into the current window.
 *
 * Weighted count = previousCount * overlapRatio + currentCount
 * where overlapRatio = 1 - (elapsedInCurrentWindow / windowSize)
 *
 * If the weighted count is below the limit the request is allowed.
 *
 * Thread-safe: uses per-key synchronization.
 */
public class SlidingWindowCounter implements RateLimiter {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, WindowState> windows;

    public SlidingWindowCounter(RateLimitConfig config) {
        this.config = config;
        this.windows = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String key) {
        WindowState state = windows.computeIfAbsent(key, k -> new WindowState());
        long now = System.currentTimeMillis();
        long windowSize = config.getWindowSizeInMillis();

        synchronized (state) {
            long currentWindowStart = now - (now % windowSize);

            // If we jumped ahead by two or more windows, both previous and current
            // counts are stale, so reset everything.
            if (currentWindowStart - state.currentWindowStart >= 2 * windowSize) {
                state.previousCount = 0;
                state.currentCount = 0;
                state.currentWindowStart = currentWindowStart;
            }
            // If we moved into the next window, rotate counts.
            else if (currentWindowStart != state.currentWindowStart) {
                state.previousCount = state.currentCount;
                state.currentCount = 0;
                state.currentWindowStart = currentWindowStart;
            }

            // Calculate how far we are into the current window (0.0 .. 1.0).
            long elapsedInCurrentWindow = now - currentWindowStart;
            double overlapRatio = 1.0 - ((double) elapsedInCurrentWindow / windowSize);

            double weightedCount = state.previousCount * overlapRatio + state.currentCount;

            if (weightedCount < config.getMaxRequests()) {
                state.currentCount++;
                return true;
            }
            return false;
        }
    }

    // -----------------------------------------------------------------------
    // Internal mutable state per key.
    // -----------------------------------------------------------------------
    private static class WindowState {
        long currentWindowStart;
        int currentCount;
        int previousCount;

        WindowState() {
            this.currentWindowStart = 0;
            this.currentCount = 0;
            this.previousCount = 0;
        }
    }
}
