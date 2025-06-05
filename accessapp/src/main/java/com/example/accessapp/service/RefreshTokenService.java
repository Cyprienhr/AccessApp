package com.example.accessapp.service;

import com.example.accessapp.entity.RefreshToken;
import com.example.accessapp.exception.TokenRefreshException;

import java.util.Optional;

/**
 * Service interface for refresh token operations.
 */
public interface RefreshTokenService {

    /**
     * Find a refresh token by token string
     *
     * @param token the token string
     * @return Optional containing the refresh token if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Create a new refresh token for a user
     *
     * @param userId the user ID
     * @param clientId the client ID for multi-tenancy support
     * @return the created refresh token
     */
    RefreshToken createRefreshToken(Long userId, Long clientId);

    /**
     * Verify if a refresh token is valid and not expired
     *
     * @param token the token to verify
     * @return the verified refresh token
     * @throws TokenRefreshException if the token is invalid or expired
     */
    RefreshToken verifyExpiration(RefreshToken token);

    /**
     * Delete a user's refresh token
     *
     * @param userId the user ID
     * @return the number of tokens deleted
     */
    int deleteByUserId(Long userId);
}