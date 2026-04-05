package com.example.ratelimiter;

/**
 * Demonstrates the pluggable rate limiting system.
 *
 * 1. Fixed Window Counter — 5 requests per 60-second window for tenant T1
 * 2. Per-key isolation — tenant T2 has its own independent quota
 * 3. Swap to Sliding Window Counter without changing business logic
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println(" DEMO 1: Fixed Window Counter (5 req / 60s window)");
        System.out.println("============================================================");

        RateLimitConfig config = new RateLimitConfig(5, 60_000); // 5 requests per minute
        RateLimiter fixedWindow = new FixedWindowCounter(config);
        RateLimiterService service = new RateLimiterService(fixedWindow);
        ExternalResourceClient externalClient = new ExternalResourceClient();
        InternalService internalService = new InternalService(service, externalClient);

        // Tenant T1 makes 7 requests that need external calls.
        // The first 5 should be allowed; requests 6 and 7 should be denied.
        System.out.println("\n--- Tenant T1: 7 external requests (limit is 5) ---");
        for (int i = 1; i <= 7; i++) {
            String result = internalService.processRequest("tenant:T1", true);
            System.out.println("  Request " + i + ": " + result);
        }

        // A request that does not need an external call is always allowed.
        System.out.println("\n--- Tenant T1: local-only request (no rate limit check) ---");
        String localResult = internalService.processRequest("tenant:T1", false);
        System.out.println("  " + localResult);

        System.out.println("\n============================================================");
        System.out.println(" DEMO 2: Per-key isolation (T1 vs T2)");
        System.out.println("============================================================");

        // Tenant T2 has its own counter — T1 being exhausted does not affect T2.
        System.out.println("\n--- Tenant T2: 3 external requests (fresh quota) ---");
        for (int i = 1; i <= 3; i++) {
            String result = internalService.processRequest("tenant:T2", true);
            System.out.println("  Request " + i + ": " + result);
        }

        System.out.println("\n============================================================");
        System.out.println(" DEMO 3: Swap to Sliding Window Counter (same config)");
        System.out.println("============================================================");

        // Swap algorithm — nothing else changes.
        RateLimiter slidingWindow = new SlidingWindowCounter(config);
        RateLimiterService service2 = new RateLimiterService(slidingWindow);
        ExternalResourceClient externalClient2 = new ExternalResourceClient();
        InternalService internalService2 = new InternalService(service2, externalClient2);

        System.out.println("\n--- Tenant T1 (sliding window): 7 external requests ---");
        for (int i = 1; i <= 7; i++) {
            String result = internalService2.processRequest("tenant:T1", true);
            System.out.println("  Request " + i + ": " + result);
        }

        System.out.println("\n--- Tenant T3 (sliding window): 5 external requests ---");
        for (int i = 1; i <= 5; i++) {
            String result = internalService2.processRequest("tenant:T3", true);
            System.out.println("  Request " + i + ": " + result);
        }

        System.out.println("\n============================================================");
        System.out.println(" DEMO 4: Different config (100 req / hr)");
        System.out.println("============================================================");

        RateLimitConfig hourlyConfig = new RateLimitConfig(100, 3_600_000);
        RateLimiter hourlyLimiter = new FixedWindowCounter(hourlyConfig);
        RateLimiterService hourlyService = new RateLimiterService(hourlyLimiter);

        System.out.println("\n--- apikey:XYZ: 3 requests (under 100/hr limit) ---");
        for (int i = 1; i <= 3; i++) {
            boolean allowed = hourlyService.tryAcquire("apikey:XYZ");
            System.out.println("  Request " + i + ": " + (allowed ? "ALLOWED" : "DENIED"));
        }

        System.out.println("\n============================================================");
        System.out.println(" All demos complete.");
        System.out.println("============================================================");
    }
}
