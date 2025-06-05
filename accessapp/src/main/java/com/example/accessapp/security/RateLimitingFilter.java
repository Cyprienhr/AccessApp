package com.example.accessapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Filter to implement rate limiting based on IP address and endpoint.
 * Protects against brute force attacks and API abuse.
 * Uses a simple in-memory implementation with sliding window approach.
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);
    
    // Cache to store request counts per IP and endpoint
    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();
    
    // Rate limit configuration
    private static final int AUTH_ENDPOINT_LIMIT = 5; // 5 requests per minute for auth endpoints
    private static final int DEFAULT_ENDPOINT_LIMIT = 60; // 60 requests per minute for other endpoints
    private static final long WINDOW_SIZE_MS = TimeUnit.MINUTES.toMillis(1); // 1 minute window
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip rate limiting for certain endpoints or HTTP methods if needed
        if (shouldSkipRateLimiting(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Get client IP address
        String clientIp = getClientIp(request);
        String endpoint = request.getRequestURI();
        String key = clientIp + ":" + endpoint;
        
        // Determine rate limit based on endpoint
        int rateLimit = endpoint.startsWith("/api/auth/") ? AUTH_ENDPOINT_LIMIT : DEFAULT_ENDPOINT_LIMIT;
        
        // Check if rate limit is exceeded
        if (isRateLimitExceeded(key, rateLimit)) {
            logger.warn("Rate limit exceeded for IP: {} on endpoint: {}", clientIp, endpoint);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Rate limit exceeded. Please try again later.\"}");
            return;
        }
        
        // Continue with the request
        filterChain.doFilter(request, response);
    }
    
    /**
     * Check if rate limit is exceeded for the given key
     * 
     * @param key The cache key (IP + endpoint)
     * @param limit The rate limit to apply
     * @return true if rate limit is exceeded, false otherwise
     */
    private boolean isRateLimitExceeded(String key, int limit) {
        long currentTime = System.currentTimeMillis();
        
        // Get or create counter for this key
        RequestCounter counter = requestCounts.computeIfAbsent(key, k -> new RequestCounter());
        
        // Clean up old requests outside the window
        counter.removeOldRequests(currentTime - WINDOW_SIZE_MS);
        
        // Check if limit is exceeded
        if (counter.getRequestCount() >= limit) {
            return true;
        }
        
        // Add current request to counter
        counter.addRequest(currentTime);
        return false;
    }
    
    /**
     * Get client IP address from request
     * Handles X-Forwarded-For header for proxy scenarios
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            // Get the first IP in the chain (client IP)
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
    
    /**
     * Determine if rate limiting should be skipped for this request
     */
    private boolean shouldSkipRateLimiting(HttpServletRequest request) {
        // Skip OPTIONS requests (pre-flight CORS)
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // Skip static resources if needed
        String path = request.getRequestURI();
        return path.startsWith("/static/") || path.startsWith("/public/");
    }
    
    /**
     * Helper class to track request counts with timestamps
     */
    private static class RequestCounter {
        private final Map<Long, Integer> requestTimestamps = new ConcurrentHashMap<>();
        
        /**
         * Add a request at the current timestamp
         */
        public void addRequest(long timestamp) {
            requestTimestamps.compute(timestamp, (k, v) -> (v == null) ? 1 : v + 1);
        }
        
        /**
         * Remove requests older than the cutoff time
         */
        public void removeOldRequests(long cutoffTime) {
            requestTimestamps.keySet().removeIf(timestamp -> timestamp < cutoffTime);
        }
        
        /**
         * Get the total request count in the current window
         */
        public int getRequestCount() {
            return requestTimestamps.values().stream().mapToInt(Integer::intValue).sum();
        }
    }
}