package com.example.accessapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Service to track login attempts and implement account lockout functionality.
 * Protects against brute force attacks by temporarily blocking accounts after multiple failed login attempts.
 */
@Service
public class LoginAttemptService {
    private static final Logger logger = LoggerFactory.getLogger(LoginAttemptService.class);
    
    // Cache of login attempts, keyed by username
    private final Map<String, LoginAttemptInfo> attemptsCache = new ConcurrentHashMap<>();
    
    // Configuration
    private static final int MAX_ATTEMPTS = 5; // Maximum number of failed attempts before lockout
    private static final long LOCKOUT_DURATION_MINUTES = 15; // Lockout duration in minutes
    
    /**
     * Record a failed login attempt for a username
     * 
     * @param username The username that failed to login
     */
    public void loginFailed(String username) {
        if (username == null) {
            return;
        }
        
        LoginAttemptInfo attemptInfo = attemptsCache.computeIfAbsent(username, k -> new LoginAttemptInfo());
        attemptInfo.addFailedAttempt();
        
        logger.debug("Failed login attempt for user: {}. Attempts: {}", username, attemptInfo.getFailedAttempts());
        
        // If max attempts reached, set lockout time
        if (attemptInfo.getFailedAttempts() >= MAX_ATTEMPTS) {
            attemptInfo.setLockoutTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(LOCKOUT_DURATION_MINUTES));
            logger.warn("Account locked for user: {} due to {} failed attempts. Locked until: {}", 
                    username, attemptInfo.getFailedAttempts(), attemptInfo.getLockoutTime());
        }
    }
    
    /**
     * Record a successful login for a username
     * Resets the failed attempt counter
     * 
     * @param username The username that successfully logged in
     */
    public void loginSucceeded(String username) {
        if (username == null) {
            return;
        }
        
        // Remove from cache on successful login
        attemptsCache.remove(username);
        logger.debug("Successful login for user: {}. Attempts reset.", username);
    }
    
    /**
     * Check if a username is currently locked out
     * 
     * @param username The username to check
     * @return true if the account is locked, false otherwise
     */
    public boolean isLocked(String username) {
        if (username == null) {
            return false;
        }
        
        LoginAttemptInfo attemptInfo = attemptsCache.get(username);
        if (attemptInfo == null) {
            return false;
        }
        
        // Check if lockout time is set and still valid
        if (attemptInfo.getLockoutTime() > 0) {
            boolean locked = System.currentTimeMillis() < attemptInfo.getLockoutTime();
            
            // If lockout has expired, remove from cache
            if (!locked) {
                attemptsCache.remove(username);
                logger.debug("Lockout expired for user: {}", username);
            }
            
            return locked;
        }
        
        return false;
    }
    
    /**
     * Get the number of minutes remaining in the lockout period
     * 
     * @param username The username to check
     * @return The number of minutes remaining, or 0 if not locked
     */
    public long getLockoutMinutesRemaining(String username) {
        if (username == null) {
            return 0;
        }
        
        LoginAttemptInfo attemptInfo = attemptsCache.get(username);
        if (attemptInfo == null || attemptInfo.getLockoutTime() <= 0) {
            return 0;
        }
        
        long remainingMs = attemptInfo.getLockoutTime() - System.currentTimeMillis();
        if (remainingMs <= 0) {
            return 0;
        }
        
        return TimeUnit.MILLISECONDS.toMinutes(remainingMs) + 1; // Round up to the next minute
    }
    
    /**
     * Get the maximum allowed attempts before lockout
     * 
     * @return The maximum number of attempts
     */
    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }
    
    /**
     * Get the lockout duration in minutes
     * 
     * @return The lockout duration
     */
    public long getLockoutDurationMinutes() {
        return LOCKOUT_DURATION_MINUTES;
    }
    
    /**
     * Helper class to store login attempt information
     */
    private static class LoginAttemptInfo {
        private int failedAttempts;
        private long lockoutTime;
        
        public void addFailedAttempt() {
            failedAttempts++;
        }
        
        public int getFailedAttempts() {
            return failedAttempts;
        }
        
        public void setLockoutTime(long lockoutTime) {
            this.lockoutTime = lockoutTime;
        }
        
        public long getLockoutTime() {
            return lockoutTime;
        }
    }
}