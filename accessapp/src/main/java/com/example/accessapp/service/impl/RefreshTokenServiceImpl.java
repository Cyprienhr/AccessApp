package com.example.accessapp.service.impl;

import com.example.accessapp.entity.RefreshToken;
import com.example.accessapp.entity.User;
import com.example.accessapp.exception.TokenRefreshException;
import com.example.accessapp.repository.RefreshTokenRepository;
import com.example.accessapp.repository.UserRepository;
import com.example.accessapp.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for refresh token operations.
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${accessapp.jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Find a refresh token by token string
     *
     * @param token the token string
     * @return Optional containing the refresh token if found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Create a new refresh token for a user
     *
     * @param userId the user ID
     * @param clientId the client ID for multi-tenancy support
     * @return the created refresh token
     */
    public RefreshToken createRefreshToken(Long userId, Long clientId) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Check if user already has a refresh token and delete it
        refreshTokenRepository.findByUser(user)
                .ifPresent(token -> refreshTokenRepository.delete(token));

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setClientId(clientId);

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Verify if a refresh token is valid and not expired
     *
     * @param token the token string to verify
     * @return the verified refresh token
     * @throws TokenRefreshException if the token is invalid or expired
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    /**
     * Delete a user's refresh token
     *
     * @param userId the user ID
     * @return the number of tokens deleted
     */
    @Transactional
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return refreshTokenRepository.deleteByUser(user);
    }
}