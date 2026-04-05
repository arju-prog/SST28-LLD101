package com.example.ratelimiter;

/**
 * Simulates calling an external paid API (e.g., a third-party geocoding
 * service, payment processor, or AI inference endpoint).
 */
public class ExternalResourceClient {

    private int callCount = 0;

    /**
     * Makes a (simulated) call to the external resource.
     *
     * @param request the request payload
     * @return a simulated response string
     */
    public String call(String request) {
        callCount++;
        // Simulate latency and a response
        return "ExternalResponse{request='" + request + "', seq=" + callCount + "}";
    }

    public int getCallCount() {
        return callCount;
    }
}
