package com.example.ratelimiter;

/**
 * Represents an internal business service that sometimes needs to call an
 * external paid resource. Before making the external call it consults the
 * {@link RateLimiterService} to ensure the caller has not exceeded its quota.
 */
public class InternalService {

    private final RateLimiterService rateLimiterService;
    private final ExternalResourceClient externalClient;

    public InternalService(RateLimiterService rateLimiterService,
                           ExternalResourceClient externalClient) {
        this.rateLimiterService = rateLimiterService;
        this.externalClient = externalClient;
    }

    /**
     * Processes a request. If the request requires an external call, the rate
     * limiter is consulted first. If the rate limit is exceeded the request is
     * rejected without touching the external resource.
     *
     * @param clientId          the rate-limiting key for this caller
     * @param needsExternalCall whether this particular request needs to call
     *                          the external resource
     * @return the result of processing
     */
    public String processRequest(String clientId, boolean needsExternalCall) {
        if (!needsExternalCall) {
            return "Processed locally for " + clientId + " (no external call needed)";
        }

        if (!rateLimiterService.tryAcquire(clientId)) {
            return "RATE_LIMITED: request denied for " + clientId
                    + " — external call quota exceeded";
        }

        String response = externalClient.call("request-from-" + clientId);
        return "Processed for " + clientId + " with external response: " + response;
    }
}
