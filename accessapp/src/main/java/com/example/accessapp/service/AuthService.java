package com.example.accessapp.service;

import com.example.accessapp.dto.JwtResponse;
import com.example.accessapp.dto.LoginRequest;
import com.example.accessapp.dto.RegisterRequest;
import com.example.accessapp.dto.TokenRefreshResponse;
import com.example.accessapp.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Service interface for authentication operations.
 */
public interface AuthService {

    /**
     * Authenticate a user and generate JWT tokens
     *
     * @param loginRequest the login request containing username and password
     * @param request the HTTP request for client information
     * @param clientId the client ID for multi-tenancy support
     * @return JWT response containing tokens and user information
     */
    JwtResponse authenticateUser(LoginRequest loginRequest, HttpServletRequest request, Long clientId);

    /**
     * Register a new user
     *
     * @param registerRequest the registration request
     * @param request the HTTP request for client information
     * @param clientId the client ID for multi-tenancy support
     * @return the created user
     */
    User registerUser(RegisterRequest registerRequest, HttpServletRequest request, Long clientId);

    /**
     * Refresh an access token using a refresh token
     *
     * @param refreshToken the refresh token
     * @param clientId the client ID for multi-tenancy support
     * @return response containing new access token and refresh token
     */
    TokenRefreshResponse refreshToken(String refreshToken, Long clientId);

    /**
     * Logout a user by invalidating their refresh token
     *
     * @param userId the user ID
     * @param request the HTTP request for client information
     * @param clientId the client ID for multi-tenancy support
     */
    void logoutUser(Long userId, HttpServletRequest request, Long clientId);

    /**
     * Blacklist a JWT token
     *
     * @param token the token to blacklist
     * @param clientId the client ID for multi-tenancy support
     */
    void blacklistToken(String token, Long clientId);

    /**
     * Check if a token is blacklisted
     *
     * @param token the token to check
     * @param clientId the client ID for multi-tenancy support
     * @return true if the token is blacklisted, false otherwise
     */
    boolean isTokenBlacklisted(String token, Long clientId);
}